import 'package:flutter_test/flutter_test.dart';
import 'package:uae_pass/uae_pass.dart';
import 'package:uae_pass/uae_pass_platform_interface.dart';
import 'package:uae_pass/uae_pass_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockUaePassPlatform
    with MockPlatformInterfaceMixin
    implements UaePassPlatform {
  @override
  Future<String> signIn() => Future.value('42');

  @override
  Future<void> setUp(String clientId, String clientSecret, bool isProduction,
      String urlScheme, String state) {
    return Future.value(null);
  }

  @override
  Future<String> getAuthToken(String code) {
    throw UnimplementedError();
  }

  @override
  Future<void> signOut() {
    // TODO: implement signOut
    throw UnimplementedError();
  }
}

void main() {
  final UaePassPlatform initialPlatform = UaePassPlatform.instance;

  test('$MethodChannelUaePass is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelUaePass>());
  });

  test('getPlatformVersion', () async {
    UaePass uaePassPlugin = UaePass();
    MockUaePassPlatform fakePlatform = MockUaePassPlatform();
    UaePassPlatform.instance = fakePlatform;

    expect(await uaePassPlugin.signIn(), '42');
  });
}
