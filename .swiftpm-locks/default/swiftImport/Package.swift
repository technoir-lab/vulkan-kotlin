// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "KotlinMultiplatformLinkedPackage",
  platforms: [
    .iOS("26.0")
  ],
  products: [
    .library(
      name: "KotlinMultiplatformLinkedPackage",
      type: .none,
      targets: ["KotlinMultiplatformLinkedPackage"]
    )
  ],
  dependencies: [
    .package(path: "subpackages/_sample"),
    .package(path: "subpackages/_volk-kotlin"),
    .package(path: "subpackages/_volk_kotlin"),
    .package(path: "subpackages/_vulkan-kotlin")
  ],
  targets: [
    .target(
      name: "KotlinMultiplatformLinkedPackage",
      dependencies: [
        .product(name: "_sample", package: "_sample"),
        .product(name: "_volk-kotlin", package: "_volk-kotlin"),
        .product(name: "_volk_kotlin", package: "_volk_kotlin"),
        .product(name: "_vulkan-kotlin", package: "_vulkan-kotlin")
      ]
    )
  ]
)
