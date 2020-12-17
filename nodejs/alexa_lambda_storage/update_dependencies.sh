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

if [[ "x$MY_LAYER_ARN" == "x" ]]; then
    echo "Set the MY_LAYER_ARN environment variable to the ARN of your AWS Lambda Layer"
    exit -1
fi

if [[ "x$MY_LAYER_ARN" == "xARN_VALUE_FROM_LAYER" ]]; then
    echo "ARN_VALUE_FROM_LAYER is an example value, you will need to get the actual ARN from you layer"
    echo "on AWS."
    exit -1
fi

# Create a temporary directory to put the packages in
tmp_dir=$(mktemp -d -t pkg-XXXXXXXXXX)
function cleanup {
    rm -rf $tmp_dir || true
}
trap cleanup EXIT
mkdir -p $tmp_dir/nodejs/node_modules

# Build the directory structure, install the modules and zip everything
cp package*.json $tmp_dir/nodejs/
pushd $tmp_dir/nodejs/
npm install
cd ..
zip -r dependencies.zip nodejs 2>&1 > /dev/null

# Go back to the original directory to the the rest of the update
popd
cp $tmp_dir/dependencies.zip .

RESULT=$(aws lambda publish-layer-version \
    --layer-name $MY_LAYER_ARN \
    --description "Dependencies for my storage project" \
    --zip-file fileb://dependencies.zip \
    --compatible-runtimes nodejs12.x)

# Get information to update the lambda
VERSION=$(echo $RESULT | jq .Version)

if [[ "x$MY_LAYER_ARN" == "x" ]]; then
    echo "Something went wrong, could not get version from publish-layer-version result:"
    echo $RESULT | jq .
    exit -1
else
    aws lambda update-function-configuration \
        --function-name $MY_LAMBDA_ARN \
        --layers $MY_LAYER_ARN:$VERSION
fi
