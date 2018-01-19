maven_jar(name="aopalliance_aopalliance",                               artifact="aopalliance:aopalliance:1.0")
maven_jar(name="com_fasterxml_jackson_core_jackson_core",               artifact="com.fasterxml.jackson.core:jackson-core:2.9.2")
maven_jar(name="com_google_api_api_common",                             artifact="com.google.api:api-common:1.2.0")
maven_jar(name="com_google_api_client_google_api_client",               artifact="com.google.api-client:google-api-client:1.23.0")
maven_jar(name="com_google_api_client_google_api_client_appengine",     artifact="com.google.api-client:google-api-client-appengine:1.23.0")
maven_jar(name="com_google_api_gax",                                    artifact="com.google.api:gax:1.15.0")
maven_jar(name="com_google_api_grpc_proto_google_common_protos",        artifact="com.google.api.grpc:proto-google-common-protos:1.0.2")
maven_jar(name="com_google_apis_google_api_services_drive",             artifact="com.google.apis:google-api-services-drive:v3-rev90-1.23.0")
maven_jar(name="com_google_appengine_appengine_api_1_sdk",              artifact="com.google.appengine:appengine-api-1.0-sdk:1.9.57")
maven_jar(name="com_google_appengine_appengine_tools_sdk",              artifact="com.google.appengine:appengine-tools-sdk:1.9.57")
maven_jar(name="com_google_appengine_tools_appengine_gcs_client",       artifact="com.google.appengine.tools:appengine-gcs-client:0.7")
maven_jar(name="com_google_auth_google_auth_library_credentials",       artifact="com.google.auth:google-auth-library-credentials:0.9.0")
maven_jar(name="com_google_auth_google_auth_library_oauth2_http",       artifact="com.google.auth:google-auth-library-oauth2-http:0.9.0")
maven_jar(name="com_google_cloud_datastore_datastore_1_proto_client",   artifact="com.google.cloud.datastore:datastore-v1-proto-client:1.5.1")
maven_jar(name="com_google_cloud_datastore_datastore_v1_protos",        artifact="com.google.cloud.datastore:datastore-v1-protos:1.5.0")
maven_jar(name="com_google_cloud_google_cloud_core",                    artifact="com.google.cloud:google-cloud-core:1.12.0")
maven_jar(name="com_google_cloud_google_cloud_core_http",               artifact="com.google.cloud:google-cloud-core-http:1.12.0")
maven_jar(name="com_google_cloud_google_cloud_datastore",               artifact="com.google.cloud:google-cloud-datastore:1.12.0")
maven_jar(name="com_google_guava_guava",                                artifact="com.google.guava:guava:23.0")
maven_jar(name="com_google_http_client_google_http_client",             artifact="com.google.http-client:google-http-client:1.23.0")
maven_jar(name="com_google_http_client_google_http_client_appengine",   artifact="com.google.http-client:google-http-client-appengine:1.23.0")
maven_jar(name="com_google_http_client_google_http_client_jackson2",    artifact="com.google.http-client:google-http-client-jackson2:1.23.0")
maven_jar(name="com_google_http_client_google_http_client_protobuf",    artifact="com.google.http-client:google-http-client-protobuf:1.23.0")
maven_jar(name="com_google_inject_extensions_guice_servlet",            artifact="com.google.inject.extensions:guice-servlet:4.0")
maven_jar(name="com_google_inject_guice",                               artifact="com.google.inject:guice:4.0")
maven_jar(name="com_google_oauth_client_google_oauth_client",           artifact="com.google.oauth-client:google-oauth-client:1.23.0")
maven_jar(name="com_google_oauth_client_google_oauth_client_java6",     artifact="com.google.oauth-client:google-oauth-client-java6:1.23.0")
maven_jar(name="com_google_oauth_client_google_oauth_client_jetty",     artifact="com.google.oauth-client:google-oauth-client-jetty:1.23.0")
maven_jar(name="com_google_oauth_client_google_oauth_client_servlet",   artifact="com.google.oauth-client:google-oauth-client-servlet:1.23.0")
maven_jar(name="com_google_protobuf_protobuf_java",                     artifact="com.google.protobuf:protobuf-java:3.5.0")
maven_jar(name="javax_inject_javax_inject",                             artifact="javax.inject:javax.inject:1")
maven_jar(name="javax_servlet_servlet_api",                             artifact="javax.servlet:servlet-api:2.5")
maven_jar(name="org_json_json",                                         artifact="org.json:json:20171018")
maven_jar(name="org_mortbay_jetty_jetty",                               artifact="org.mortbay.jetty:jetty:6.1.26")
maven_jar(name="org_mortbay_jetty_jetty_util",                          artifact="org.mortbay.jetty:jetty-util:6.1.26")
maven_jar(name="org_threeten_threetenbp",                               artifact="org.threeten:threetenbp:1.3.6")

http_archive(
    name = "io_bazel_rules_appengine",
    url = "https://github.com/bazelbuild/rules_appengine/archive/55b86458274eab6c46480ed649d4c13939e9b4ac.tar.gz",
    strip_prefix = "rules_appengine-55b86458274eab6c46480ed649d4c13939e9b4ac",
)

load("@io_bazel_rules_appengine//appengine:appengine.bzl", "appengine_repositories")
appengine_repositories()


rig_sha="c0dcaa85aeb0e266fb531e84018efdf173d4be17"

# This should be nailed to a particular sha and have a sha256 specified before release.
# (It's just that things are changing quickly right now)
http_archive(
    name = "rules_intellij_generate",
    url = "https://github.com/sconover/rules_intellij_generate/archive/%s.tar.gz" % rig_sha,
    strip_prefix = "rules_intellij_generate-%s/rules" % rig_sha,
)
load("@rules_intellij_generate//:def.bzl", "repositories_for_intellij_generate")
repositories_for_intellij_generate()

http_archive(
    name = "rules_junit5",
    url = "https://github.com/sconover/rules_intellij_generate/archive/%s.tar.gz" % rig_sha,
    strip_prefix = "rules_intellij_generate-%s/rules_junit5" % rig_sha,
)
load("@rules_junit5//:def.bzl", "junit5_repositories")
junit5_repositories()
