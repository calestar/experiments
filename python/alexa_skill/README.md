# Dummy Alexa Skill

This project hosts a simple lambda that I used to play with Alexa Skills. My goal was to understand how to configure
everything and extract values from what the user said. This repository only contains the AWS Lambda code that I used.

## AWS Setup
1. Create an Amazon Developer account (https://developer.amazon.com/alexa/console/ask)
2. Create a AWS Account (https://portal.aws.amazon.com/billing/signup#/start)
3. In the Amazon Developer portal, create a new Alexa Skill and configure it to use a custom lambda
4. In the AWS Portal, create a new lambda using Python 3.7 and configure it to be triggered by an Alexa Skills Kit
5. Copy GUIDs around to link the Lambda to the Skill
6. Deploy the lambda code from this directory by running: `MY_LAMBDA_ARN=ARN_VALUE_FROM_LAMBDA ./publish.sh`

## Local Setup
I ran this experiment using my 32bit Raspberry Pi, therefore could not use AWS CLI v2. THe following assumes that you are using AWS CLI v1.
1. Install AWS CLI (https://docs.aws.amazon.com/cli/latest/userguide/install-cliv1.html)
2. Run `aws configure` and follow the directives
