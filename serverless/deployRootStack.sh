#! /bin/bash

aws cloudformation create-stack --stack-name ServerlessRoot --template-body file://serverlessRootCloudFormation.yml
aws cloudformation wait stack-create-complete --stack-name ServerlessRoot
