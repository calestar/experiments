# Dummy Alexa Skill

This project hosts a simple lambda that I used to play with Alexa Skills. My goal was to understand
how to store data in two specific scenarios: session-based and persistent.

I will not go over the whole setup, but I created 5 distinct intents on the Alexa Developer Console:
- Add
- Current
- Forget
- Persist
- Restore

The first 3 are used to play with the in-session storage, and the last two for the persistent storage.

## Deploying the lambda
I ran this experiment using my 32bit Raspberry Pi, therefore could not use AWS CLI v2. THe following assumes that you are using AWS CLI v1.
1. Install AWS CLI (https://docs.aws.amazon.com/cli/latest/userguide/install-cliv1.html)
2. Run `aws configure` and follow the directives
3. Deploy the lambda code from this directory by running: `MY_LAMBDA_ARN=ARN_VALUE_FROM_LAMBDA ./publish.sh`
