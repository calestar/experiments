#! /usr/bin/env bash
# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

createTunnel() {
  ssh -R $SSHF_TUNNEL_REMOTE_PORT:localhost:$SSHF_TUNNEL_LOCAL_PORT -S $SSHF_CONTROL_SOCKET -M -fN $SSHF_REMOTE_USERNAME@$SSHF_REMOTE_SERVER
  if [[ $? -eq 0 ]]; then
    echo Tunnel to remote server created successfully
  else
    echo An error occurred creating a tunnel to remote server. Result was $?
  fi
}

ssh -S $SSHF_CONTROL_SOCKET -O check $SSHF_REMOTE_USERNAME@$SSHF_REMOTE_SERVER 2>&1 | grep "Master running"
if [[ $? -ne 0 ]]; then
  echo Creating new tunnel connection
  createTunnel
fi
