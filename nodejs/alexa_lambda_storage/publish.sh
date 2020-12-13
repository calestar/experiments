#! /usr/bin/env bash
# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

# Validate environment
if [[ "x$MY_LAMBDA_ARN" == "x" ]]; then
    echo "Set the MY_LAMBDA_ARN environment variable to the ARN of your AWS Lambda"
    exit -1
fi

if [[ "x$MY_LAMBDA_ARN" == "xARN_VALUE_FROM_LAMBDA" ]]; then
    echo "ARN_VALUE_FROM_LAMBDA is an example value, you will need to get the actual ARN from you lambda"
    echo "on AWS."
    exit -1
fi

npm install
zip -r package.zip lambda.js node_modules 2>&1 > /dev/null

aws lambda update-function-code \
    --function-name  $MY_LAMBDA_ARN \
    --zip-file fileb://package.zip
