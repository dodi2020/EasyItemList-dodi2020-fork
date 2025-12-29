# GitHub Actions Workflows

## Build Workflow

The `build.yml` workflow automatically builds the mod on every push and pull request.

### What it does:
1. Sets up Java 21 (required for Minecraft 1.20.5+)
2. Caches Gradle dependencies for faster builds
3. Builds the mod using `./gradlew build`
4. Uploads the compiled JAR files as artifacts

### Accessing Build Artifacts:
After a successful build, you can download the compiled mod JAR from the Actions tab:
1. Go to the Actions tab in GitHub
2. Click on the latest build workflow run
3. Scroll down to "Artifacts" section
4. Download "EasyItemList-artifacts"

The artifacts are kept for 30 days.

### Network Access:
The GitHub Actions runners have proper network access to:
- Maven Central
- Fabric Maven (including SNAPSHOT repositories)
- Gradle Plugin Portal

This means the build will work in CI even if it fails locally due to network restrictions.
