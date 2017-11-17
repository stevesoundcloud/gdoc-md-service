load("@rules_intellij_generate//:def.bzl", "intellij_modules_xml")
intellij_modules_xml(
    name = "modules_xml",
    deps = [
        "//convert:iml",
        "//service:iml",
    ]
)

load("@rules_intellij_generate//:def.bzl", "intellij_project")
intellij_project(
    name="idea_project",
    intellij_modules_xml=":modules_xml",
)