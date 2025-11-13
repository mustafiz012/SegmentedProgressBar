# Maven Central Publishing Setup Guide

This guide explains how to publish this library to Maven Central via the **Central Publisher Portal**.

> **Important:** As of June 30, 2025, OSSRH (OSS Repository Hosting) has reached end of life. All publishing now goes through the [Central Publisher Portal](https://central.sonatype.org/pages/ossrh-eol/).

## Prerequisites

1. **Central Publisher Portal Account**
   - Log in to [Maven Central Portal](https://central.sonatype.com/) using your existing OSSRH credentials (if you had one)
   - If you don't have an account, create one at the Central Portal
   - Verify your namespace ownership for `io.github.mustafiz012` in the portal
   - Generate a Portal user token for publishing (replaces the old OSSRH token)

2. **GPG Key for Signing**
   - Generate a GPG key if you don't have one:
     ```bash
     gpg --gen-key
     ```
   - Export your public key to a keyserver:
     ```bash
     gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
     ```

## Configuration

### Option 1: Using local.properties (Recommended for Local Development)

Add your credentials to `local.properties` (this file is gitignored by default):

```properties
# Sonatype OSSRH Credentials
ossrhUsername=your-sonatype-username
ossrhPassword=your-sonatype-password

# GPG Signing (if using in-memory keys)
signingKeyId=your-gpg-key-id
signingKey=your-base64-encoded-private-key
signingPassword=your-gpg-key-password
```

**Note:** The build system checks credentials in this order:
1. `local.properties` (highest priority)
2. `gradle.properties`
3. Environment variables

### Option 2: Using gradle.properties

You can also add credentials to `gradle.properties`:

```properties
# Sonatype OSSRH Credentials
ossrhUsername=your-sonatype-username
ossrhPassword=your-sonatype-password

# GPG Signing (if using in-memory keys)
signingKeyId=your-gpg-key-id
signingKey=your-base64-encoded-private-key
signingPassword=your-gpg-key-password
```

### Option 3: Using Environment Variables (Recommended for CI/CD)

Set the following environment variables:

```bash
export OSSRH_USERNAME=your-sonatype-username
export OSSRH_PASSWORD=your-sonatype-password
export SIGNING_KEY_ID=your-gpg-key-id
export SIGNING_KEY=your-base64-encoded-private-key
export SIGNING_PASSWORD=your-gpg-key-password
```

### Option 3: Using GPG Command Line (Default)

If you don't set the signing properties, the build will use your default GPG setup (`~/.gnupg/`). This is the simplest option for local development.

## Publishing Steps

### 1. Build and Test Locally

```bash
./gradlew :segmentedprogressbar:build
./gradlew :segmentedprogressbar:publishToMavenLocal
```

### 2. Generate Portal User Token

Before publishing, you need to generate a user token:

1. Log in to [Maven Central Portal](https://central.sonatype.com/)
2. Navigate to your account settings
3. Generate a new Portal user token
4. Add this token to your `local.properties` as `ossrhPassword` (the token replaces your password)

**Important:** Use your Portal username and the generated token (not your account password) for publishing.

### 3. Publish to Maven Central

```bash
./gradlew :segmentedprogressbar:publishToMavenCentral
```

This will:
- Build the library
- Sign all artifacts with GPG
- Upload to Central Publisher Portal

**Note:** The current configuration uses the Central Publisher Portal API endpoint. If you encounter issues with the standard `maven-publish` plugin, you may need to:

1. **Verify the OSSRH Staging API Service URL** - Contact [Central Support](https://central.sonatype.org/pages/support.html) to confirm the correct endpoint for the compatibility service
2. **Use a third-party Gradle plugin** - Consider plugins like `gradle-nexus-publish-plugin` or similar that support the Portal API
3. **Use Portal API directly** - Implement custom publishing tasks using the Portal API

For the most up-to-date publishing methods, refer to the [Central Publishing Guide](https://central.sonatype.org/publish/publish-guide/).

### 4. Manage Publication via Portal

After publishing, manage your publication through the Central Portal:

1. Log in to [Maven Central Portal](https://central.sonatype.com/)
2. Navigate to your publications
3. Review and manage your published artifacts
4. The artifacts will be synced to Maven Central (usually within 10-30 minutes)

### 4. Verify Publication

After release, verify your library is available:

- Maven Central: https://repo1.maven.org/maven2/io/github/mustafiz012/segmentedprogressbar/
- Search: https://search.maven.org/search?q=g:io.github.mustafiz012

## Troubleshooting

### Signing Issues

If you get signing errors:
- Ensure GPG is installed: `gpg --version`
- Check your GPG key: `gpg --list-secret-keys`
- For in-memory keys, ensure the private key is base64 encoded

### Credential Issues

- Verify your Central Portal credentials are correct
- Ensure you're using a Portal user token (not the old OSSRH password)
- Ensure your namespace (`io.github.mustafiz012`) is verified in the portal
- Check that your GitHub repository matches the namespace
- If you don't see your namespaces, contact [Central Support](https://central.sonatype.org/pages/support.html)

### POM Validation Errors

Maven Central has strict POM requirements:
- All required fields must be present (name, description, url, licenses, developers, scm)
- URLs must be valid and accessible
- License information must be correct

## Version Management

- Use semantic versioning (e.g., 1.0.0, 1.0.1, 1.1.0)
- Update the version in `segmentedprogressbar/build.gradle`
- Create a Git tag for each release
- Update CHANGELOG.md with release notes

## CI/CD Integration

For automated publishing, you can set up GitHub Actions or similar CI/CD:

1. Store credentials as repository secrets
2. Set up GPG key as a secret
3. Automate the publish and release process

Example GitHub Actions workflow:
```yaml
- name: Publish to Maven Central
  run: ./gradlew :segmentedprogressbar:publishToMavenCentral
  env:
    OSSRH_USERNAME: ${{ secrets.OSSRH_USERNAME }}
    OSSRH_PASSWORD: ${{ secrets.OSSRH_PASSWORD }}  # Portal user token
    SIGNING_KEY_ID: ${{ secrets.SIGNING_KEY_ID }}
    SIGNING_KEY: ${{ secrets.SIGNING_KEY }}
    SIGNING_PASSWORD: ${{ secrets.SIGNING_PASSWORD }}
```

## Additional Resources

- [OSSRH EOL and Migration Guide](https://central.sonatype.org/pages/ossrh-eol/)
- [Central Publisher Portal](https://central.sonatype.com/)
- [Central Publishing Guide](https://central.sonatype.org/publish/publish-guide/)
- [GPG Signing Guide](https://central.sonatype.org/publish/requirements/gpg/)
- [Maven Central Requirements](https://central.sonatype.org/publish/requirements/)
- [Central Support](https://central.sonatype.org/pages/support.html)

