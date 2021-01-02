// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
const Alexa = require('ask-sdk-core');

// Create the DynamoDB service object
var Dynamo = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'});
const DynamoTableName = 'AZSkill';

const AddIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'Add';
  },
  handle(handlerInput) {
    let attributes = handlerInput.attributesManager.getSessionAttributes();
    const current_value = attributes.current_value || 0;
    attributes.current_value = current_value + 1;

    handlerInput.attributesManager.setSessionAttributes(attributes);

    return handlerInput.responseBuilder
      .speak(`Now at ${attributes.current_value}`)
      .withShouldEndSession(false)
      .getResponse();
  }
};

const CurrentIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'Current';
  },
  handle(handlerInput) {
    const attributes = handlerInput.attributesManager.getSessionAttributes();
    const current_value = attributes.current_value;
    let text = 'No value defined';

    if (current_value) {
      text = `Current value is ${current_value}`;
    }

    return handlerInput.responseBuilder
      .speak(text)
      .withShouldEndSession(false)
      .getResponse();
  }
};

const ForgetIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'Forget';
  },
  handle(handlerInput) {
    let attributes = handlerInput.attributesManager.getSessionAttributes();
    attributes.current_value = 0;

    handlerInput.attributesManager.setSessionAttributes(attributes);

    return handlerInput.responseBuilder
      .speak('Value just got reset')
      .withShouldEndSession(false)
      .getResponse();
  }
};

const PersistIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'Persist';
  },
  async handle(handlerInput) {
    const attributes = handlerInput.attributesManager.getSessionAttributes();
    const current_value = attributes.current_value;
    const userId = Alexa.getUserId(handlerInput.requestEnvelope);

    var params = {
      TableName: DynamoTableName,
      Item: {
        'Client' : userId,
        'Value' : current_value
      }
    };

    let text = 'Value is now stored';
    try {
      await Dynamo.put(params).promise();
    } catch(error) {
      text = 'Error storing value';
    }

    return handlerInput.responseBuilder
      .speak(text)
      .withShouldEndSession(false)
      .getResponse();
  }
};

const RestoreIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'Restore';
  },
  async handle(handlerInput) {
    let attributes = handlerInput.attributesManager.getSessionAttributes();
    const userId = Alexa.getUserId(handlerInput.requestEnvelope);

    var params = {
      TableName: DynamoTableName,
      Key:{
          "Client": userId,
      }
    };

    let text = 'Could not restore value';
    try {
      let entry = await Dynamo.get(params).promise();
      attributes.current_value = entry.Item.Value;
      handlerInput.attributesManager.setSessionAttributes(attributes);

      text = `Value restored, now ${attributes.current_value}`;
    } catch(error) {
      // numm numm
    }

    return handlerInput.responseBuilder
      .speak(text)
      .withShouldEndSession(false)
      .getResponse();
  }
};

const ErrorHandler = {
  canHandle() {
    return true;
  },
  handle(handlerInput, error) {
    console.log(`Error: ${error.message}`);

    return handlerInput.responseBuilder
      .speak('Sorry, what?')
      .reprompt('Sorry, what?')
      .getResponse();
  },
};

let skill;

exports.handler = async function (event, context) {
  if (!skill) {
    skill = Alexa.SkillBuilders.custom()
      .addRequestHandlers(
        AddIntentHandler,
        CurrentIntentHandler,
        ForgetIntentHandler,
        PersistIntentHandler,
        RestoreIntentHandler,
      )
      .addErrorHandlers(ErrorHandler)
      .create();
  }

  return await skill.invoke(event, context);
};
