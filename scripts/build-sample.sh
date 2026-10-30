#!/usr/bin/env bash
set -euo pipefail

root_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
sample_dir="$root_dir/sample/apple"
build_dir="$root_dir/sample/build"
configuration="${CONFIGURATION:-Debug}"
target="${1:-}"

case "$configuration" in
    Debug|Release)
        ;;
    *)
        echo "Unsupported configuration: $configuration" >&2
        exit 2
        ;;
esac

case "${target}" in
    macos|ios_simulator|ios_device)
        ;;
    *)
        echo "Usage: $0 {macos|ios_simulator|ios_device}" >&2
        exit 2
        ;;
esac

build() {
    local target="$1"
    local destination sdk
    local -a build_settings

    case "$target" in
        macos)
            destination='platform=macOS,arch=arm64'
            sdk=macosx
            build_settings=(CODE_SIGN_IDENTITY=-)
            ;;
        ios_simulator)
            destination='generic/platform=iOS Simulator'
            sdk=iphonesimulator
            build_settings=(CODE_SIGN_IDENTITY=-)
            ;;
        ios_device)
            destination='generic/platform=iOS'
            sdk=iphoneos
            build_settings=(CODE_SIGNING_ALLOWED=NO)
            ;;
    esac

    xcodebuild -project "$sample_dir/Sample.xcodeproj" \
        -scheme Sample \
        -configuration "$configuration" \
        -sdk "$sdk" \
        -destination "$destination" \
        -derivedDataPath "$build_dir/xcode/$target" \
        -quiet -hideShellScriptEnvironment \
        "${build_settings[@]}" build
}

build "$target"
