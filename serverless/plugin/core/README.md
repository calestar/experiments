# Sample plugin to use as core for Serverless projects

I love to use serverless to deploy my AWS lambda projects, but there are a few small details that I'd want from it.

Amongst other things:
- Prevent all my S3 buckets from *EVER* being made public
- Always put a log retention on my log groups
- Have an easy way of knowing who deployed a lambda and which Git SHA was deployed
- A few per-stage configurations in there and other options and validations

Serverless plugins to the rescue!

All my _issues_ can be fixed by using already proven plugins, but I wanted to play with plugins anyway :-)

I built this as a core extension, so that I can reuse this _master_ extension on every project. A business could use the same
concept (have one core extension, doing a lot of small things) instead of installing 20 different extentions and copy pasting
configuration on all projects.
