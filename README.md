https://github.com/GoogleCloudPlatform/app-maven-plugin

run local dev server:
mvn -DskipTests=true -e appengine:run

visit: http://localhost:8080
You can also [browse the local datastore](http://localhost:8080/_ah/admin).

deploy
mvn appengine:deploy


## OAuth notes - reconsidered

The playground approach

w/ client id and secret, add this redirect url

https://developers.google.com/oauthplayground

- Go to the oauth dev playground:
  https://developers.google.com/oauthplayground/
- Step 1: select scopes:
  Drive api v3, select:
    https://www.googleapis.com/auth/drive.metadata.readonly
    https://www.googleapis.com/auth/drive.readonly
- Click "Authorize APIs"
- Authorization flow happens - log in and click "Allow"
- (Redirected back to oauth playground)
- Click "Exchange auth code for tokens"
- Make note of the access and request token (keep these very secret)

- Now enter these tokens in
(hackey dev mode)
- visit: http://localhost:8080
  - Entry in access and refresh tokens
- Somewhere under http://localhost:8080/_ah/admin/datastore , you should see "service.properties" saved

- Now try a conversion: http://localhost:8080/convert


-----------

GAE/Intellij: https://cloud.google.com/tools/intellij/docs/quickstart-IDEA
  Add GAE Standard support to module via https://github.com/GoogleCloudPlatform/google-cloud-intellij/issues/1203#issuecomment-318904153
  Ultimate edition needed in order to run AE in local mode (see GH issue)
  
https://cloud.google.com/appengine/docs/standard/python/microservices-on-app-engine

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
     - for dev: http://localhost:8080/fetch-oauth-token
     - for prod: https://foo.appspot.com/fetch-oauth-token

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