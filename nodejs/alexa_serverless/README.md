# alexa_serverless

In this experiment, I deploy my alexa lambda experiment using the [Serverless](https://www.serverless.com/) Framework.

## Local setup

Since I wanted to remove a few personal IDs, you'll need to modify `serverless.yml`. The only value you'll need to modify:
 - _alexaSkill_: this value should be the ID of your Alexa Skill Kit

## Deploying

Once the local setup is done, you'll be able to deploy the template lambda using:
```bash
serverless deploy --region us-east-1
```

This will generate, using Cloud Formation under the hood, your lambda, link it to your Alexa Skill and create a log group with a 5 day retention.

## Destroying everything

Serverless has a `remove` command that will destroy your Cloud Formation stack for you, simply run the following to do so:
```bash
serverless remove --region us-east-1
```
