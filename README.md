## Running Locally

```
bazel build //...
(bazel does a bunch of stuff...)

cd service/webapp
../../bazel-bin/service/backend --port=12345

...should result in output ending in...
INFO: Dev App Server is now running

curl localhost:12345/foo
{'requested' : '/foo'}
```

## AppEngine Deployment

- Set up a new AppEngine app [here](https://console.cloud.google.com/projectselector/appengine/create?lang=java&st=true).
- run `bazel-bin/service/backend.deploy <your-appengine-service-id>`, (and the first time, go through the authorization process)

Once deployed, find the service AppEngine url in the console, and try:
```
curl https://your-appengine-service-id.appspot.com/foo
{'requested' : '/foo'}
```

## Scenario GDocs

See [this gdrive folder](https://drive.google.com/drive/folders/1SyDE0Ult3-PTh-dHAfOOkr7pikkKmASW).

## Project conventions

### Git

- <i>NEVER COMMIT BINARIES</i>: jars, images, whatever. `.gitignore` binaries and such as needed.
- `branches-are-named-like-this`
- All merges to master should be based on feature branches, which, unless there are really exceptional
circumstances, should be squashed / single-commit, with some (30-120s) attention paid
to a good final commit message, that is appropriately descriptive for an audience of future maintainers
that are not the same person as the author.

## References

- [bazel/java/appengine tutorial](https://docs.bazel.build/versions/master/tutorial/backend-server.html)