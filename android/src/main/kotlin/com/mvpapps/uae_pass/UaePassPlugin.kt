package com.mvpapps.uae_pass

import ae.sdg.libraryuaepass.*
import ae.sdg.libraryuaepass.UAEPassController.getAccessToken
import ae.sdg.libraryuaepass.UAEPassController.resume
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry;

/** UaePassPlugin */
class UaePassPlugin: FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.NewIntentListener  {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var binding: FlutterPlugin.FlutterPluginBinding
  private lateinit var requestModel: UAEPassAccessTokenRequestModel
 
  private lateinit var activityPluginBinding: ActivityPluginBinding



  override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
    attachToActivity(binding)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    disposeActivity()
  }

  override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {
    attachToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    disposeActivity()
  }

  private fun attachToActivity(binding: ActivityPluginBinding) {
    this.activityPluginBinding = binding
    binding.addOnNewIntentListener(this)
  }

  private fun disposeActivity() {
  }


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "uae_pass")
    channel.setMethodCallHandler(this)
    binding = flutterPluginBinding;
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if(call.method=="set_up_environment")
    {

      Log.d("intent","setup")

    }else if(call.method=="sign_in")
    {
      requestModel = UAEPassRequestModels.getAuthenticationRequestModel(this.activityPluginBinding.getActivity())
      getAccessToken(this.activityPluginBinding.getActivity(), requestModel, object : UAEPassAccessTokenCallback {
        override fun getToken(accessToken: String?, state: String, error: String?) {

          if (error != null) {
            result.error("ERROR", error, null);

          } else {
            result.success(accessToken)

          }
        }
      })
    }

    else {
      result.notImplemented()
    }
  }
  override  fun onNewIntent(intent: Intent): Boolean {
    Log.d("intent","inside intent")
    handleIntent(intent)
    return false
  }
  private fun handleIntent(intent: Intent?) {
    Log.d("intent","inside intent")
    if (intent != null && intent.data != null) {
      if ("uaepassdemoappds" == intent.data!!.scheme) {
        resume(intent.dataString)
      }
    }
  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
