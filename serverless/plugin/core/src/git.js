'use strict';

const { spawnSync } = require('child_process');

const { allResourcesIn } = require('./common');


const CMD_BRANCH_NAME = ['git', 'symbolic-ref', '--short', 'HEAD'];
const CMD_IS_DIRTY = ['git', 'status', '--short'];
const CMD_GET_SHA = ['git', 'rev-parse', '--verify', 'HEAD'];
const CMD_GET_USERNAME = ['id', '-u', '-n'];


function getCommandResult(command) {
  try {
    let result = spawnSync(command[0], command.slice(1));
    if (result.stdout) {
      return result.stdout.toString().trim();
    }
    console.log('While running: ', command.join(' '));
    console.log('Could not get result: ', result)
    console.log('  stdout: ', (result.stdout || '').toString());
    console.log('  stderr: ', (result.stderr || '').toString());
    return undefined;
  } catch (error) {
    console.log('While running: ', command.join(' '));
    console.log('Could not get result: ', error)
    console.log('  stdout: ', (error.stdout || '').toString());
    console.log('  stderr: ', (error.stderr || '').toString());
  }
}

function processGitData(serverless, options) {
  const branchName = getCommandResult(CMD_BRANCH_NAME);
  const isDirty = !!getCommandResult(CMD_IS_DIRTY);
  const sha = getCommandResult(CMD_GET_SHA);
  const username = getCommandResult(CMD_GET_USERNAME);

  const build = `${branchName}${isDirty?'(dirty)':''}@${sha}`

  for (const [resourceName, resource] of allResourcesIn(serverless)) {
    if (resource.Type === 'AWS::Lambda::Function') {
      resource.Properties.Environment.Variables.GIT_BUILD_DATA = build;
      resource.Properties.Environment.Variables.DEPLOYED_BY = username;
    }
  }
}

module.exports = {
  processGitData,
};
