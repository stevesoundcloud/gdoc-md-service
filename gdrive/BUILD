package(default_visibility = ["//:__subpackages__"])

load("@rules_intellij_generate//:def.bzl",
    "intellij_source_java_library")

intellij_source_java_library(
    name="lib",
    source_folder_to_wildcard_map={"javasrc":"**/*.java"},
    deps=[
        "//common:iml",

        "@com_fasterxml_jackson_core_jackson_core//jar",
        "@com_google_api_client_google_api_client//jar",
        "@com_google_api_client_google_api_client_appengine//jar",
        "@com_google_apis_google_api_services_drive//jar",
        "@com_google_guava_guava//jar",
        "@com_google_http_client_google_http_client//jar",
        "@com_google_http_client_google_http_client_appengine//jar",
        "@com_google_http_client_google_http_client_jackson2//jar",
        "@com_google_oauth_client_google_oauth_client//jar",
        "@com_google_oauth_client_google_oauth_client_java6//jar",
        "@com_google_oauth_client_google_oauth_client_jetty//jar",
        "@javax_inject_javax_inject//jar",
        "@javax_servlet_servlet_api//jar",
        "@org_mortbay_jetty_jetty//jar",
        "@org_mortbay_jetty_jetty_util//jar",
    ]
)

java_binary(
    name = "foo",
    main_class = "gdocmd.gdrive.Foo",
    runtime_deps = [":lib"],
)

# load("@rules_junit5//:def.bzl", "JUNIT5_MINIMAL_DEPS")
# intellij_source_java_library(
#     name="test_lib",
#     source_folder_to_wildcard_map={"javatest":"**/*.java"},
#     deps=[
#         ":lib",
#     ] + JUNIT5_MINIMAL_DEPS,
# )

load("@rules_intellij_generate//:def.bzl", "intellij_iml")
intellij_iml(
    name = "iml",
    java_source=":lib",
    # test_java_source=":test_lib",
)

# load("@rules_junit5//:def.bzl", "junit5_all_in_package_test")
# junit5_all_in_package_test(
#     name="tests",
#     java_package="gdocmd",
#     runtime_deps=[":test_lib"]
# )
