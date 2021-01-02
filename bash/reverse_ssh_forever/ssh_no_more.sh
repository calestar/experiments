#! /usr/bin/env bash
# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

killTunnel() {
  ssh -S $SSHF_CONTROL_SOCKET -O exit $SSHF_REMOTE_USERNAME@$SSHF_REMOTE_SERVER
  if [[ $? -eq 0 ]]; then
    echo Tunnel killed correctly
  else
    echo Could not kill tunnel. Result was $?
  fi
}

ssh -S $SSHF_CONTROL_SOCKET -O check $SSHF_REMOTE_USERNAME@$SSHF_REMOTE_SERVER 2>&1 | grep "Master running"
if [[ $? -eq 0 ]]; then
  echo Killing tunnel connection
  killTunnel
fi
