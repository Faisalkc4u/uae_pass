import 'package:flutter/material.dart';

import 'package:uae_pass/uae_pass.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _result = 'Unknown';
  final _uaePassPlugin = UaePass();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            MaterialButton(
              onPressed: () async {
                await _uaePassPlugin.setUpSandbox();
                // await _uaePassPlugin.setUpEnvironment(
                //   "clientId",
                //   "secret",
                //   "uaepassdemoappDS://",
                //   isProduction: true,
                // );
                try {
                  _result = await _uaePassPlugin.signIn();
                } catch (e) {
                  _result = e.toString();
                }
                setState(() {});
              },
              child: const Text('Sign in'),
            ),
            Center(
              child: Text('Running on: $_result\n'),
            ),
          ],
        ),
      ),
    );
  }
}
