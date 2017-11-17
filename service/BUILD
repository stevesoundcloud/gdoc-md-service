package(default_visibility = ["//visibility:public"])
load("@io_bazel_rules_appengine//appengine:appengine.bzl", "appengine_war")
load("@rules_intellij_generate//:def.bzl",
    "intellij_source_java_library")

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
        "@javax_servlet_api//jar",
        "@com_google_appengine_java//:api",
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
