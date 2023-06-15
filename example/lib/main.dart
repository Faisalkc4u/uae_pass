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
  String? _authToken;
  String? _accessToken;
  String? _error;
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
        body: Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              MaterialButton(
                onPressed: () async {
                  _authToken = null;
                  _accessToken = null;
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
                    _accessToken = await _uaePassPlugin.signIn();
                    _authToken =
                        await _uaePassPlugin.getAccessToken(_accessToken!);
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
              if (_accessToken != null)
                TextFormField(
                  decoration:
                      const InputDecoration(label: Text("Access token")),
                  initialValue: _accessToken,
                ),
              if (_authToken != null)
                TextFormField(
                  decoration: const InputDecoration(label: Text("Auth token")),
                  initialValue: _authToken,
                ),
            ],
          ),
        ),
      ),
    );
  }
}
