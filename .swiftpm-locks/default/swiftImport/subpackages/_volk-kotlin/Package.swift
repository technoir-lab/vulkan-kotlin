// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_volk-kotlin",
  platforms: [
    .iOS("26.0")
  ],
  products: [
    .library(
      name: "_volk-kotlin",
      type: .none,
      targets: ["_volk-kotlin"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/technoir-lab/vulkan-swift",
      exact: "1.0.2"
    )
  ],
  targets: [
    .target(
      name: "_volk-kotlin",
      dependencies: [
        .product(
          name: "VulkanDriver",
          package: "vulkan-swift"
        ),
        .product(
          name: "VulkanValidation",
          package: "vulkan-swift"
        )
      ]
    )
  ]
)
