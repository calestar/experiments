// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

'use strict';

const get = require('lodash/get');

const { Config } = require('./config')
const { allResourcesIn } = require('./common');


function processS3Buckets(serverless, options) {
  const skippedBuckets = get(serverless, 'service.custom.core.skippedBuckets', []);

  for (const [resourceName, resource] of allResourcesIn(serverless)) {
    if (skippedBuckets.includes(resourceName)) {
       continue;
     }

     if (resource.Type === 'AWS::S3::Bucket') {
       resource.Properties.PublicAccessBlockConfiguration = {
         BlockPublicAcls: true,
         BlockPublicPolicy: true,
         IgnorePublicAcls: true,
         RestrictPublicBuckets: true,
       };
     }
   }
}

module.exports = {
  processS3Buckets,
};
