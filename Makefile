SHELL := sh
GRADLE := sh ./gradlew

.PHONY: clean check format abi test docs publish-local build run-sample-macos run-sample-ios help
.DEFAULT_GOAL := help

clean: ## Remove build outputs
	@$(GRADLE) clean $(GRADLE_ARGS)

check: ## Run checks
	@$(GRADLE) check $(GRADLE_ARGS)

format: ## Format code and build scripts
	@$(GRADLE) ktlintFormat sortDependencies $(GRADLE_ARGS)

abi: ## Update Kotlin ABI files
	@$(GRADLE) updateKotlinAbi $(GRADLE_ARGS)

test: ## Run multiplatform tests
	@$(GRADLE) allTests $(GRADLE_ARGS)

docs: ## Generate API documentation
	@$(GRADLE) :vulkan-kotlin:dokkaGenerate $(GRADLE_ARGS)

publish-local: ## Publish artifacts to Maven Local
	@$(GRADLE) publishToMavenLocal $(GRADLE_ARGS)

build: ## Build sample app
	@scripts/build-sample.sh macos
	@scripts/build-sample.sh ios_simulator
	@scripts/build-sample.sh ios_device

run-sample-macos: ## Build and run the macOS sample app
	@scripts/build-sample.sh macos
	@scripts/run-sample.sh macos

run-sample-ios: ## Build and run the iOS sample app on simulator
	@scripts/build-sample.sh ios_simulator
	@scripts/run-sample.sh ios

help: ## Show this help
	@printf 'Available targets:\n'
	@color=''; reset=''; \
	if [ -t 1 ] && [ "$${TERM:-dumb}" != dumb ] && [ -z "$${NO_COLOR:-}" ]; then \
		color=$$(printf '\033[36m'); reset=$$(printf '\033[0m'); \
	fi; \
	awk -v color="$$color" -v reset="$$reset" '/^[[:alnum:]_-]+:/ { target = $$0; sub(/:.*/, "", target); description = $$0; if (sub(/.*##[[:space:]]*/, "", description) == 0) description = ""; printf "  %s%s%s%*s %s\n", color, target, reset, 17 - length(target), "", description }' $(MAKEFILE_LIST)
	@printf '\nPass Gradle options with GRADLE_ARGS, e.g. make check GRADLE_ARGS="--info --rerun-tasks"\n'
