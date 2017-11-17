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
