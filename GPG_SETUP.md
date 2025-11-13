# GPG Setup Guide for Maven Central Publishing

GPG (GNU Privacy Guard) signing is **required** for publishing to Maven Central. This guide will help you set it up.

## Why GPG is Required

Maven Central requires all artifacts to be signed with GPG to ensure authenticity and integrity. This is a security requirement that cannot be bypassed.

## Installation

### macOS

Install GPG using Homebrew:

```bash
brew install gnupg
```

Or download from: https://gpgtools.org/

### Verify Installation

```bash
gpg --version
```

You should see version information if GPG is installed correctly.

## Generate a GPG Key

### Step 1: Generate Your Key

```bash
gpg --full-generate-key
```

Follow the prompts:
1. **Key type**: Choose `(1) RSA and RSA` (default)
2. **Key size**: Choose `4096` (recommended)
3. **Expiration**: Choose your preference (e.g., `1y` for 1 year, `0` for no expiration)
4. **Name**: Enter your name (e.g., "Md. Mustafizur Rahman")
5. **Email**: Enter your email (e.g., "mustafiz012@outlook.com")
6. **Comment**: Optional comment
7. **Passphrase**: Create a strong passphrase (you'll need this for signing)

### Step 2: List Your Keys

```bash
gpg --list-secret-keys --keyid-format LONG
```

You'll see output like:
```
sec   rsa4096/ABCD1234EFGH5678 2025-01-01 [SC]
      ABCDEF1234567890ABCDEF1234567890ABCD1234
uid                 [ultimate] Md. Mustafizur Rahman <mustafiz012@outlook.com>
```

The part after `rsa4096/` (e.g., `ABCD1234EFGH5678`) is your **Key ID**.

### Step 3: Export Your Public Key to Keyserver

Maven Central requires your public key to be available on a public keyserver:

```bash
# Replace YOUR_KEY_ID with your actual key ID from step 2
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

You can also try other keyservers:
```bash
gpg --keyserver pgp.mit.edu --send-keys YOUR_KEY_ID
gpg --keyserver keys.openpgp.org --send-keys YOUR_KEY_ID
```

### Step 4: Verify Key is on Keyserver

Wait a few minutes, then verify:

```bash
gpg --keyserver keyserver.ubuntu.com --recv-keys YOUR_KEY_ID
```

## Configure Your Build

The build is already configured to use GPG command line by default. You don't need to add anything to `local.properties` for GPG - it will use your default GPG setup automatically.

However, if you want to use in-memory keys (useful for CI/CD), you can add to `local.properties`:

```properties
# GPG Signing Configuration (optional - only if using in-memory keys)
# signingKeyId=YOUR_KEY_ID
# signingKey=YOUR_BASE64_ENCODED_PRIVATE_KEY
# signingPassword=YOUR_GPG_PASSPHRASE
```

**For local development, you don't need these** - the build will use GPG command line and prompt you for your passphrase when needed.

## Test GPG Signing

Test that GPG signing works:

```bash
./gradlew :segmentedprogressbar:publishToMavenLocal
```

This will:
- Build your library
- Sign it with GPG (you'll be prompted for your passphrase)
- Publish to your local Maven repository

If you see signing errors, check:
- GPG is installed: `gpg --version`
- Your key exists: `gpg --list-secret-keys`
- Your public key is on keyserver (wait a few minutes after uploading)

## Publishing to Maven Central

Once GPG is set up, you can publish:

```bash
./gradlew :segmentedprogressbar:publishToMavenCentral
```

You'll be prompted for your GPG passphrase during the signing process.

## Troubleshooting

### "gpg: signing failed: No secret key"

- Make sure you have a GPG key: `gpg --list-secret-keys`
- The key ID must match if you specified one

### "gpg: signing failed: Inappropriate ioctl for device"

This happens in some CI/CD environments. Solution:
- Use in-memory keys instead of GPG command line
- Or set: `export GPG_TTY=$(tty)`

### "gpg: keyserver receive failed: No keyserver available"

- Try a different keyserver
- Wait a few minutes after uploading (propagation delay)
- Verify with: `gpg --keyserver keyserver.ubuntu.com --recv-keys YOUR_KEY_ID`

## Additional Resources

- [GPG Official Documentation](https://www.gnupg.org/documentation/)
- [Maven Central GPG Requirements](https://central.sonatype.org/publish/requirements/gpg/)
- [GPG Tools for macOS](https://gpgtools.org/)

