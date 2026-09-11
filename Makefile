.PHONY: all build run-sample-macos run-sample-ios clean help

all: build

build:
	@scripts/build-sample.sh macos
	@scripts/build-sample.sh ios_simulator
	@scripts/build-sample.sh ios_device

run-sample-macos:
	@scripts/build-sample.sh macos
	@scripts/run-sample.sh macos

run-sample-ios:
	@scripts/build-sample.sh ios_simulator
	@scripts/run-sample.sh ios

clean:
	@./gradlew clean

help:
	@echo 'Targets:'
	@echo '  make build            Build sample app'
	@echo '  make run-sample-macos Build and run the macOS sample app'
	@echo '  make run-sample-ios   Build and run the iOS sample app on simulator'
	@echo '  make clean            Clean build outputs'
