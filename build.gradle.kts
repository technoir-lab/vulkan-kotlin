plugins {
    id("io.technoirlab.conventions.root")
}

dependencies {
    nmcpAggregation(project(":volk-kotlin"))
    nmcpAggregation(project(":vulkan-kotlin"))
}
