# Service Discovery Through Lambda Extension

I read about Lambda Extensions a little while back and wanted to play with it. In this experiment, I write a poor-man Service Discovery
using Lambda Extensions.

Basically, your lambda sets two environment variables:
- `NEEDED_SERVICES`: comma separated list of services that are needed, even supports `myService==0.1.0` syntax
- `SERVICES_FILE`: where to store the resolved services (please put this in `/tmp`)

Serverless will deploy your lambda, the discovery layer and a DynamoDB table where services are stored.

When your lambda starts the first time (aka, only once per lambda instance), the extension will resolve the services and write the file.

This could easily be extended by listening to the `INVOKE` event on the extension to be able to refresh the service list. Or simply add a timer
in the extension to do it.
