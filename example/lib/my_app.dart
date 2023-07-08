import 'dart:io';

import 'package:flutter/material.dart';
import 'package:uae_pass/uae_pass.dart';

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _authToken;
  String? accessToken;
  String? _error;
  final _uaePassPlugin = UaePass();

  @override
  void initState() {
    super.initState();
    print('initState called');
  }

  @override
  void dispose() {
    super.dispose();
    print('disposed called');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            MaterialButton(
              onPressed: () async {
                _authToken = null;
                accessToken = null;
                _error = null;
                setState(() {});
                await _uaePassPlugin.setUpSandbox();
                // await _uaePassPlugin.setUpEnvironment(
                //   "clientId",
                //   "secret",
                //   "uaepassdemoappDS://",
                //   isProduction: true,
                // );
                try {
                  accessToken = await _uaePassPlugin.signIn();
                  print("my acces token11 $accessToken");

                  if (Platform.isIOS) {
                    _authToken =
                        await _uaePassPlugin.getAccessToken(accessToken!);
                  }
                } catch (e) {
                  _error = e.toString();
                }
                setState(() {});
              },
              child: const Text('Sign in'),
            ),
            if (_error != null)
              TextFormField(
                initialValue: _error,
              ),
            Text(
              "$accessToken",
              style: const TextStyle(color: Colors.red),
            ),
            if (accessToken != null)
              TextFormField(
                decoration: const InputDecoration(label: Text("Access token")),
                initialValue: accessToken,
              ),
            if (_authToken != null)
              TextFormField(
                decoration: const InputDecoration(label: Text("Auth token")),
                initialValue: _authToken,
              ),
          ],
        ),
      ),
    );
  }
}
