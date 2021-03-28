'use strict';

const Config = {
  'dev': {
    RetentionInDays: 30,
  },
  'uat': {
    RetentionInDays: 30,
  },
  'staging': {
    RetentionInDays: 30,
  },
  'prod': {
    RetentionInDays: 10,
  }
};

module.exports = {
  Config,
};
