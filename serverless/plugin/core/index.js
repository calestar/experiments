'use strict';

const { validateStage } = require('./src/validations');
const { processLogRetention } = require('./src/log_retention');
const { processS3Buckets } = require('./src/s3_buckets');
const { processGitData } = require('./src/git');


class ServerlessPlugin {
  constructor(serverless, options) {
    this.serverless = serverless;
    this.options = options;

    this.hooks = {
      'after:package:initialize': this.afterPackageInitialize.bind(this),
      'before:package:finalize': this.beforePackageFinalize.bind(this),
    };
  }

  afterPackageInitialize() {
    validateStage(this.options);
  }

  beforePackageFinalize() {
    processLogRetention(this.serverless, this.options);
    processS3Buckets(this.serverless, this.options);
    processGitData(this.serverless, this.options);
  }
}

module.exports = ServerlessPlugin;
