#!/usr/bin/env bash
set -euo pipefail

root_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
configuration="${CONFIGURATION:-Debug}"
bundle_id="io.technoirlab.vulkan.sample"

case "$configuration" in
    Debug|Release)
        ;;
    *)
        echo "Unsupported configuration: $configuration" >&2
        exit 2
        ;;
esac

run_macos() {
    local macos_app="$root_dir/sample/build/xcode/macos/Build/Products/$configuration/Sample.app"
    if [[ ! -d "$macos_app" ]]; then
        echo "macOS app not found: $macos_app" >&2
        echo "Run scripts/build-sample.sh macos first." >&2
        exit 1
    fi
    open "$macos_app"
}

run_ios() {
    local ios_app="$root_dir/sample/build/xcode/ios_simulator/Build/Products/$configuration-iphonesimulator/Sample.app"
    if [[ ! -d "$ios_app" ]]; then
        echo "iOS Simulator app not found: $ios_app" >&2
        echo "Run scripts/build-sample.sh ios_simulator first." >&2
        exit 1
    fi
    xcrun simctl install booted "$ios_app"
    xcrun simctl launch --terminate-running-process booted "$bundle_id"
}

case "${1:-}" in
    macos)
        run_macos
        ;;
    ios)
        run_ios
        ;;
    *)
        echo "Usage: $0 {macos|ios}" >&2
        exit 2
        ;;
esac
