# Reserve SSH Forever
The goal of this project is to document how to get a permanent reverse SSH Tunnel going between two machines.

## Scripts
### _ssh_forever.sh_
This script is used to establish; it is safe to use this script over-and-over and will only open the tunnel if it is not already established.

### _ssh_no_more.sh_
This script is used to close the tunnel.

## Setup
All of the scripts in this directory are meant to be used with environment variables. First thing is to take the sample file (_sample.env_), copy it somewhere and put in values that make sense for your setup.

Once the file is ready, you can manually try the scripts:
```
$ source path/to/my.env
$ ./ssh_forever.sh
Creating new tunnel connection
Tunnel to remote server created successfully
$ ./ssh_no_more.sh
Master running (pid=31871)
Killing tunnel connection
Exit request sent.
Tunnel killed correctly
```

Once these are confirmed as working correctly, you can edit your local crontab file to get the script running automatically.
