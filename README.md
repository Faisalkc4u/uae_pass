# uae_pass

Un-official UAE Pass Flutter plugin for Android and iOS.

## Getting Started

- Add the plugin to your pubspec.yaml file

```yaml
uae_pass: ^0.0.1
```

or

```yaml
uae_pass:
  git:
    url: https://github.com/Faisalkc4u/uae_pass.git
    ref: main
```

- Run flutter pub get

```bash
flutter pub get
```

- Import the package

```dart
import 'package:uae_pass/uae_pass.dart';

```

- Initialize the plugin - Sandbox

```dart
  await _uaePassPlugin.setUpSandbox();
```

- Initialize the plugin - Production

```dart
 await _uaePassPlugin.setUpEnvironment(
                  "< client Id here >",
                  "< client secret here >",
                  "< redirect url scheme here >",
                  isProduction: true, // set to false for sandbox
                );
```

- Call the authenticate method

```dart
  final result = await _uaePassPlugin.signIn();
```

- Check the result

```dart
  if (result != null) {
    if (result is UaePassError) {
      print("Error: ${result.message}");
    } else if (result is UaePassSuccess) {
      print("Success: ${result.accessToken}");
    }
  }
```

## iOS Setup

- Add the following to your Info.plist file

```xml
  <key>LSApplicationQueriesSchemes</key>
    <array>
      <string>uaepass</string>
      <string>uaepassqa</string>
      <string>uaepassdev</string>
      <string>uaepassstg</string>
    </array>
  <key>CFBundleURLTypes</key>
  <array>
    <dict>
      <key>CFBundleTypeRole</key>
      <string>Editor</string>
      <key>CFBundleURLName</key>
      <string>You App URL Scheme here</string> // e.g. uaepassdemoappDS (use for Sandbox )
      <key>CFBundleURLSchemes</key>
      <array>
        <string>You App URL Scheme here</string>// e.g. uaepassdemoappDS (same as above)
      </array>
    </dict>
  </array>
```
