'use strict';

const get = require('lodash/get');


function *allResourcesIn(serverless) {
  const generatedResources = get(serverless, 'service.provider.compiledCloudFormationTemplate.Resources', {});
  const manualResources = get(serverless, 'service.resources.Resources', {});

  for (const resourceName in generatedResources) {
    yield [resourceName, generatedResources[resourceName]];
  }

  for (const resourceName in manualResources) {
    yield [resourceName, manualResources[resourceName]];
  }
}

module.exports = {
  allResourcesIn,
};
