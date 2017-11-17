package(default_visibility = ["//:__subpackages__"])

load("@rules_intellij_generate//:def.bzl", "intellij_source_java_library")

intellij_source_java_library(
    name="lib",
    source_folder_to_wildcard_map={"javasrc":"**/*.java"},
    deps = [
        "@com_fasterxml_jackson_core_jackson_core//jar",
        "@com_google_api_client_google_api_client//jar",
        "@com_google_api_client_google_api_client_appengine//jar",
        "@com_google_appengine_appengine_api_1_sdk//jar",
        "@com_google_appengine_tools_appengine_gcs_client//jar",
        "@com_google_auth_google_auth_library_oauth2_http//jar",
        "@com_google_guava_guava//jar",
        "@com_google_http_client_google_http_client//jar",
        "@com_google_http_client_google_http_client_appengine//jar",
        "@com_google_http_client_google_http_client_jackson2//jar",
        "@com_google_oauth_client_google_oauth_client//jar",
        "@javax_inject_javax_inject//jar",
    ]
)

load("@rules_junit5//:def.bzl", "JUNIT5_MINIMAL_DEPS")
intellij_source_java_library(
    name="test_lib",
    source_folder_to_wildcard_map={"javatest":"**/*.java"},
    deps=[
        ":lib",
    ] + JUNIT5_MINIMAL_DEPS,
)

load("@rules_intellij_generate//:def.bzl", "intellij_iml")
intellij_iml(
    name = "iml",
    java_source=":lib",
    test_java_source=":test_lib",
)

load("@rules_junit5//:def.bzl", "junit5_all_in_package_test")
junit5_all_in_package_test(
    name="tests",
    java_package="gdocmd",
    runtime_deps=[":test_lib"]
)
