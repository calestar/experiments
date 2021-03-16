// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

const fs = require('fs').promises;
const AWS = require('aws-sdk');
const { register, next } = require('./extensions-api');

const Dynamo = new AWS.DynamoDB.DocumentClient({ apiVersion: '2012-08-10' });
const DynamoTableName = 'Services';

function parseNeededServices() {
  const config = process.env.NEEDED_SERVICES;
  const services = {};

  if (config) {
    config.split(',').forEach((service) => {
      service = service.trim();

      const parts = service.split('==');
      services[parts[0]] = {
        semver: parts[1] || 'latest',
      };
    });
  }

  return services;
}

async function getServices(config) {
  for (const [service, value] of Object.entries(config)) {
    var params = {
      TableName: DynamoTableName,
      Key:{
        name: service,
        semver: value.semver,
      }
    };

    try {
      const result = await Dynamo.get(params).promise();
      const arn = result.Item.arn;
      console.log(`Found service '${service}' (${value.semver}): ${arn}`)
      config[service].arn = arn;
    } catch (error) {
      console.log('Error: ', error);
    }
  }
}

async function writeServicesFile(config) {
  const services_file = process.env.SERVICES_FILE;

  if (services_file) {
    const json = JSON.stringify(config);
    await fs.writeFile(services_file, json, 'utf8');
    console.log(json);
  }
}

function shutdown() {
  process.exit(0);
}

(async function main() {
  process.on('SIGINT', () => shutdown());
  process.on('SIGTERM', () => shutdown());

  console.log('Starting Service Discovery ...');
  const config = parseNeededServices();
  await getServices(config);
  await writeServicesFile(config);

  // Service discovery done, now start the event loop
  const extensionId = await register('service_discovery');
  while (true) {
    const event = await next(extensionId);

    switch (event.eventType) {
      case EventType.SHUTDOWN:
        shutdown();
        break;
      default:
        throw new Error('unknown event: ' + event.eventType);
    }
  }
})();
