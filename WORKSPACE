http_archive(
    name = "io_bazel_rules_appengine",
    url = "https://github.com/bazelbuild/rules_appengine/archive/55b86458274eab6c46480ed649d4c13939e9b4ac.tar.gz",
    strip_prefix = "rules_appengine-55b86458274eab6c46480ed649d4c13939e9b4ac",
)

load("@io_bazel_rules_appengine//appengine:appengine.bzl", "appengine_repositories")
appengine_repositories()


# This should be nailed to a particular sha and have a sha256 specified before release.
# (It's just that things are changing quickly right now)
http_archive(
    name = "rules_intellij_generate",
    url = "https://github.com/sconover/rules_intellij_generate/archive/5e9d9c0b82356c962cc385da3c2dcad7c24ccba4.tar.gz",
    strip_prefix = "rules_intellij_generate-5e9d9c0b82356c962cc385da3c2dcad7c24ccba4/rules",
)
load("@rules_intellij_generate//:def.bzl", "repositories_for_intellij_generate")
repositories_for_intellij_generate()

http_archive(
    name = "rules_junit5",
    url = "https://github.com/sconover/rules_intellij_generate/archive/5e9d9c0b82356c962cc385da3c2dcad7c24ccba4.tar.gz",
    strip_prefix = "rules_intellij_generate-5e9d9c0b82356c962cc385da3c2dcad7c24ccba4/rules_junit5",
)
load("@rules_junit5//:def.bzl", "junit5_repositories")
junit5_repositories()
