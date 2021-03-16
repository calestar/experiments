// Heavily based on AWS Sample from:
// https://github.com/aws-samples/aws-lambda-extensions/blob/main/nodejs-example-logs-api-extension/nodejs-example-logs-api-extension/extensions-api.js

const fetch = require('node-fetch');

const baseUrl = `http://${process.env.AWS_LAMBDA_RUNTIME_API}/2020-01-01/extension`;

async function register(extension_name) {
  const res = await fetch(`${baseUrl}/register`, {
    method: 'post',
    body: JSON.stringify({
      'events': [
        'SHUTDOWN'
      ],
    }),
    headers: {
      'Content-Type': 'application/json',
      // The extension name must match the file name of the extension itself that's in /opt/extensions/
      'Lambda-Extension-Name': extension_name,
    }
  });

  if (!res.ok) {
    console.error('register failed', await res.text());
  }

  return res.headers.get('lambda-extension-identifier');
}

async function next(extensionId) {
  const res = await fetch(`${baseUrl}/event/next`, {
    method: 'get',
    headers: {
      'Content-Type': 'application/json',
      'Lambda-Extension-Identifier': extensionId,
    }
  });

  if (!res.ok) {
    console.error('next failed', await res.text());
    return null;
  }

  return await res.json();
}

module.exports = {
  register,
  next,
};
