https://github.com/GoogleCloudPlatform/app-maven-plugin

run local dev server:
STORAGE_BUCKET_NAME=sc-gdocmd-dev mvn -e appengine:run


You can also [browse the local datastore](http://localhost:8080/_ah/admin).

TODO:

- oauth flow - oauth token/code saves property named user.<user-id> in system.properties file
  - need to implement a DataStore that's backed by this file

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

## OAuth notes

Q: What out of this do users who run the service need to do?

https://stackoverflow.com/a/22865286

- Choose or make the account, ALL of whose gdrive docs will be visible to
  the converter service. STRONGLY suggest that you make a dedicated
  account with a name that's very obvious, like "GdocToMarkdownBot".
  Log in to this account.
- Go to GCloud Console
  - Make a project (e.g., "gdoc-to-md-service")
  - Go to the API Library ("Use Google APIs")
  - Search for "Drive API"
  - Enable this API
  - Click "Manage"
  - In the left-hand menu, select "Credentials"
  - Click "Create credentials (dropdown) -> OAuth Client ID"
  - Select "Web Application"
  - Click "Create"
  - Client ID and Secret will pop up. Record these in a safe place,
    like a password manager app (and NOT in source control).
  - Authorized redirect URIs - must contain http://localhost:59402/Callback
    in order for the Foo example to work

..I think users just need to do the following part...

- Go to the OAuth Playground
  - https://developers.google.com/oauthplayground/
  - Select "drive readonly" as the api scope
  - Check the box to get a refresh token
  - Click Exchange auth code for tokens
  - Get the Refresh Token, and store this in a secure place. This is the
    stand-in for an account username/password


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