'use strict';

const { Config } = require('./config')


const ValidStages = Object.keys(Config);


function validateStage(options) {
  const stage = options.stage;
  if (!ValidStages.includes(stage)) {
    throw new Error(`Invalid stage, valid values are: ${ValidStages.join(', ')}`);
  }
}

module.exports = {
  validateStage,
};
