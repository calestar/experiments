// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

'use strict';

const { Config } = require('./config');
const { allResourcesIn } = require('./common');


function processLogRetention(serverless, options) {
  const stage = options.stage;
  for (const [resourceName, resource] of allResourcesIn(serverless)) {
    if (resource.Type === 'AWS::Logs::LogGroup') {
      resource.Properties.RetentionInDays = Config[stage].RetentionInDays;
    }
  }
}

module.exports = {
  processLogRetention,
};
