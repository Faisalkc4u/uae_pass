import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:uae_pass/uae_pass_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelUaePass platform = MethodChannelUaePass();
  const MethodChannel channel = MethodChannel('uae_pass');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, null);
  });
}
