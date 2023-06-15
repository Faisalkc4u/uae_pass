import 'package:flutter/services.dart';

import 'uae_pass_platform_interface.dart';

class UaePass {
  Future<void> setUpSandbox() async {
    UaePassPlatform.instance
        .setUp("sandbox_stage", "sandbox_stage", false, "uaepassdemoappDS://");
  }

  Future<void> setUpEnvironment(
    String clientId,
    String clientSecret,
    String urlScheme, {
    bool isProduction = false,
  }) async {
    UaePassPlatform.instance
        .setUp(clientId, clientSecret, isProduction, urlScheme);
  }

  Future<String?> signIn() async {
    try {
      return await UaePassPlatform.instance.signIn();
    } on PlatformException catch (e) {
      throw (e.message ?? "Unknown error");
    } catch (e) {
      throw ("Unknown error");
    }
  }
}
