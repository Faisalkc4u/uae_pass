import 'package:flutter/material.dart';
import 'package:uae_pass_flutter/uae_pass.dart';

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
  }

  @override
  void dispose() {
    super.dispose();
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
                // await _uaePassPlugin.setUpSandbox();

                await _uaePassPlugin.setUpEnvironment(
                  "sandbox_stage",
                  "sandbox_stage",
                  "myapp",
                  "123123123",
                  isProduction: false,
                );
                try {
                  accessToken = await _uaePassPlugin.signIn();
                  debugPrint("my access token11 $accessToken");
                } catch (e) {
                  _error = e.toString();
                }
                setState(() {});
              },
              child: const Text('Sign in'),
            ),
            MaterialButton(
              onPressed: () async {
                _authToken = null;
                accessToken = null;
                _error = null;
                await _uaePassPlugin.signOut();
                setState(() {});
              },
              child: const Text('Sign out'),
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
