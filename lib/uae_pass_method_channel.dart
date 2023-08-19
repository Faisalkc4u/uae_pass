import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'uae_pass_platform_interface.dart';

/// An implementation of [UaePassPlatform] that uses method channels.
class MethodChannelUaePass extends UaePassPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('uae_pass');

  @override
  Future<void> setUp(
    String clientId,
    String clientSecret,
    bool isProduction,
    String urlScheme,
    String state,
    String redirectUri,
  ) async {
    await methodChannel
        .invokeMethod<void>('set_up_environment', <String, String>{
      'client_id': clientId,
      'client_secret': clientSecret,
      'environment': isProduction ? 'production' : 'qa',
      "redirect_uri_login": urlScheme,
      "scheme": urlScheme,
      'state': state,
      "redirect_url": redirectUri,
    });
  }

  @override
  Future<String> signIn() async {
    final result = await methodChannel.invokeMethod<String>('sign_in');
    return result!;
  }

  @override
  Future<String> getAuthToken(String code) async {
    final result = await methodChannel.invokeMethod<String>('auth_token', {
      'code': code,
    });
    return result!;
  }

  @override
  Future<String> getAccessToken(String code) async {
    final result = await methodChannel.invokeMethod<String>('auth_token', {
      'code': code,
    });
    return result!;
  }

  @override
  Future<void> signOut() async {
    await methodChannel.invokeMethod<void>('sign_out');
  }
}
