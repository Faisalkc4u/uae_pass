# uae_pass

Un-official UAE Pass Flutter plugin for Android and iOS.

## Getting Started

- Add the plugin to your pubspec.yaml file

```yaml
uae_pass_flutter: ^0.0.1
```

or

```yaml
uae_pass_flutter:
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
final _uaePassPlugin = UaePass();


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
                  redirect_url: "< redirect url here >",
                  scope: "< scope here >",// by default urn:uae:digitalid:profile
                );
```

# Scopes are as follows

- urn:uae:digitalid:profile:general
- urn:uae:digitalid:profile

# Call the authenticate method

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

## Android Setup

- Update android:launchMode="singleTask" the AndroidManifest.xml file

```xml

 <activity
            android:name=".MainActivity"
            android:exported="true"

            android:launchMode="singleTask"

            android:theme="@style/LaunchTheme"
            android:configChanges="orientation|keyboardHidden|keyboard|screenSize|smallestScreenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
            android:hardwareAccelerated="true"
            android:windowSoftInputMode="adjustResize">
            .....

            </activity>

```

- Set up the intent filter in your AndroidManifest.xml file

```xml
            <intent-filter >
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />


                <data
                    android:host="success"
                    android:scheme="<Your App Scheme>" />

                <data
                    android:host="failure"
                    android:scheme="<Your App Scheme>" />

            </intent-filter>

```

Note: incase kotlin error, add the following to your build.gradle file

```gradle
buildscript {
    // update this line
    ext.kotlin_version = '1.7.10'
```

[Read Common issues](https://docs.uaepass.ae/faq/common-integration-issues)

## Access Token

![Get the code from sign in method](https://github.com/Faisalkc4u/uae_pass/blob/main/screenshots/iphone.png?raw=true)
![Convert on access code to access code](https://github.com/Faisalkc4u/uae_pass/blob/main/screenshots/postman_key_setup.png?raw=true)

![Convert on access code to access code](https://github.com/Faisalkc4u/uae_pass/blob/main/screenshots/postman_body_and_response.png?raw=true)

```curl
curl --location --request POST 'https://stg-id.uaepass.ae/idshub/token?grant_type=authorization_code&redirect_uri=https://stg-selfcare.uaepass.ae&code=bfe96299-83f4-3ee9-80e4-56c24f5265d3' \
--header 'Content-Type: multipart/form-data' \
--header 'Authorization: Basic c2FuZGJveF9zdGFnZTpzYW5kYm94X3N0YWdl'
```

Refer to [Official Documentation](https://docs.uaepass.ae/guides/authentication/web-application/2.-obtaining-the-access-token)

# Access User Data

```curl
curl --location 'https://stg-id.uaepass.ae/idshub/userinfo' \
--header 'Authorization: Bearer token_here' \
```

Refer to [Official Documentation](https://docs.uaepass.ae/guides/authentication/web-application/3.-obtaining-authenticated-user-information-from-the-access-token)
