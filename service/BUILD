package(default_visibility = ["//visibility:public"])
load("@io_bazel_rules_appengine//appengine:appengine.bzl", "appengine_war")
load("@rules_intellij_generate//:def.bzl", "intellij_source_java_library")

appengine_war(
    name = "backend",
    data = [":webapp"],
    data_path = "/service/webapp",
    jars = [":app_deploy.jar"],
)

filegroup(
    name = "webapp",
    srcs = glob(["webapp/**/*"]),
)

intellij_source_java_library(
    name="lib",
    source_folder_to_wildcard_map={"javasrc":"**/*.java"},
    deps=[
        "//common:iml",
        "//gdrive:iml",

        "@aopalliance_aopalliance//jar",
        "@com_fasterxml_jackson_core_jackson_core//jar",

        "@com_google_appengine_appengine_tools_sdk//jar",
        "@com_google_appengine_appengine_api_1_sdk//jar",

        "@com_google_oauth_client_google_oauth_client_servlet//jar",

        "@com_google_api_api_common//jar",
        "@com_google_api_client_google_api_client//jar",
        "@com_google_api_client_google_api_client_appengine//jar",
        "@com_google_api_gax//jar",
        "@com_google_api_grpc_proto_google_common_protos//jar",
        "@com_google_apis_google_api_services_drive//jar",
        "@com_google_appengine_tools_appengine_gcs_client//jar",
        "@com_google_auth_google_auth_library_credentials//jar",
        "@com_google_auth_google_auth_library_oauth2_http//jar",
        "@com_google_cloud_datastore_datastore_1_proto_client//jar",
        "@com_google_cloud_datastore_datastore_v1_protos//jar",
        "@com_google_cloud_google_cloud_core//jar",
        "@com_google_cloud_google_cloud_datastore//jar",
        "@com_google_cloud_google_cloud_core_http//jar",
        "@com_google_guava_guava//jar",
        "@com_google_inject_guice//jar",
        "@com_google_inject_extensions_guice_servlet//jar",
        "@com_google_http_client_google_http_client//jar",
        "@com_google_http_client_google_http_client_appengine//jar",
        "@com_google_http_client_google_http_client_jackson2//jar",
        "@com_google_http_client_google_http_client_protobuf//jar",
        "@com_google_oauth_client_google_oauth_client//jar",
        "@com_google_protobuf_protobuf_java//jar",
        "@javax_inject_javax_inject//jar",
        "@javax_servlet_api//jar",
        "@org_json_json//jar",
        "@org_threeten_threetenbp//jar",
    ]
)

# We only need a java_library, but create a java_binary because we need a bundle
# of all of the dependencies (a deploy jar) to pass to the appengine_war. So we
# specify a non-existant class as the main_class (as the "binary" will never be
# run directly, only used as a library).
java_binary(
    name = "app",
    main_class = "does.not.exist",
    runtime_deps = [":lib"],
)

load("@rules_intellij_generate//:def.bzl", "intellij_iml")
intellij_iml(
    name = "iml",
    java_source=":lib",
)
