package(default_visibility = ["//:__subpackages__"])

load("@rules_intellij_generate//:def.bzl",
    "intellij_source_java_library")

intellij_source_java_library(
    name="lib",
    source_folder_to_wildcard_map={"javasrc":"**/*.java"},
    deps=[
        "//common:iml",
    ]
)

load("@rules_junit5//:def.bzl", "JUNIT5_MINIMAL_DEPS")
intellij_source_java_library(
    name="test_lib",
    source_folder_to_wildcard_map={"javatest":"**/*.java"},
    deps=[
        "//common:iml",

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
