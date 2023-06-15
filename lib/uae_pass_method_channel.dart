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
  ) async {
    await methodChannel
        .invokeMethod<void>('set_up_environment', <String, String>{
      'client_id': clientId,
      'client_secret': clientSecret,
      'environment': isProduction ? 'production' : 'qa',
      "redirect_uri_login": urlScheme,
      'state': state
    });
  }

  @override
  Future<String> signIn() async {
    final result = await methodChannel.invokeMethod<String>('sign_in');
    return result!;
  }
}
