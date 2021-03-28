// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

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
