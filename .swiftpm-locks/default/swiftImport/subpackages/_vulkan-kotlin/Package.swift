// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_vulkan-kotlin",
  platforms: [
    .iOS("26.0")
  ],
  products: [
    .library(
      name: "_vulkan-kotlin",
      type: .none,
      targets: ["_vulkan-kotlin"]
    )
  ],
  dependencies: [
  ],
  targets: [
    .target(
      name: "_vulkan-kotlin",
      dependencies: [
      ]
    )
  ]
)
