package com.mvpapps.uae_pass

import android.widget.Toast
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.mvpapps.uae_pass.UAEPassRequestModels

import ae.sdg.libraryuaepass.*
import ae.sdg.libraryuaepass.UAEPassController.getAccessToken
import ae.sdg.libraryuaepass.UAEPassAccessCodeCallback
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel


/** UaePassPlugin */
class UaePassPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var binding: FlutterPlugin.FlutterPluginBinding
  private lateinit var requestModel: UAEPassAccessTokenRequestModel
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "uae_pass")
    channel.setMethodCallHandler(this)
    binding = flutterPluginBinding;
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if(call.method=="set_up_environment")
    {
       requestModel = UAEPassRequestModels.getAuthenticationRequestModel(binding.getApplicationContext())

    }else if(call.method=="sign_in")
    {
      getAccessToken(binding.getApplicationContext(), requestModel, object : UAEPassAccessTokenCallback {
        override fun getToken(accessToken: String?, state: String, error: String?) {
          if (error != null) {
            Toast.makeText(
                    binding.getApplicationContext(),
                    "Error while getting access token",
                    Toast.LENGTH_SHORT
            ).show()
          } else {
            Toast.makeText(binding.getApplicationContext(), "Access Token Received", Toast.LENGTH_SHORT)
                    .show()
          }
        }
      })
    }
    else if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
