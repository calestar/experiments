# alexa_sam

In this experiment, I deploy my alexa lambda experiment using AWS Serverless Application Model (SAM).

## Local setup

Since I wanted to remove a few personal IDs, you'll need to modify `template.yaml`. The two values that will need to be modified are:
 - _AlexaSkillKit_: this value should be the ID of your Alexa Skill Kit
 - _FunctionRole_: this value should be the ARN of the role you want your lambda to use

## Deploying

Once the local setup is done, you'll be able to deploy the template lambda using:
```bash
sam build
sam deploy
```

This will generate, using Cloud Formation under the hood, your lambda, link it to your Alexa Skill and create a log group with a 3 day retention.

## Destroying everything

Since the whole deployment was done through Cloud Formation, you'll need to go on the AWS Console's Cloud Formation section to delete everything.
