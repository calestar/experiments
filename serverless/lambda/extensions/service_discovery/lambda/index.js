// Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

const fs = require('fs').promises;

exports.handler = async function (event, context) {
  const services_file = process.env.SERVICES_FILE;

  if (services_file) {
    const content = await fs.readFile(services_file);
    const json = JSON.parse(content.toString());
    console.log(json);
  }

  return 'OK';
};
