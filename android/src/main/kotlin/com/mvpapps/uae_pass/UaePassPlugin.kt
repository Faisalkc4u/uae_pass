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
import android.webkit.CookieManager
// create a class that implements the PluginRegistry.NewIntentListener interface
 


/** UaePassPlugin */
class UaePassPlugin: FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.NewIntentListener {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var binding: FlutterPlugin.FlutterPluginBinding
  private lateinit var requestModel: UAEPassAccessTokenRequestModel
 
  private lateinit var activityPluginBinding: ActivityPluginBinding
  private lateinit var flutterResult: Result


  override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
     Log.d("intent","onAttachedToActivity")
     
     if (this::activityPluginBinding.isInitialized) {
     Log.d("intent","onAttachedToActivity- removeOnNewIntentListener")
      disposeActivity()
    }
    binding.addOnNewIntentListener(this)
    attachToActivity(binding)
    handleIntent(binding.getActivity().getIntent())
  }

  override fun onDetachedFromActivityForConfigChanges() {
    Log.d("intent","onDetachedFromActivityForConfigChanges")
    disposeActivity()
  }

  override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {
     Log.d("intent","onReattachedToActivityForConfigChanges")
    if (this::activityPluginBinding.isInitialized) {
      disposeActivity()
    } 
    binding.addOnNewIntentListener(this)
    attachToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    Log.d("intent","onDetachedFromActivity")
    disposeActivity()
  }

  private fun attachToActivity(binding: ActivityPluginBinding) {
    this.activityPluginBinding = binding
  }

  private fun disposeActivity() {
    Log.d("intent","disposeActivity")
     if (this::activityPluginBinding.isInitialized) {
      activityPluginBinding.removeOnNewIntentListener(this)
     }
    
    activityPluginBinding = null!!
  }


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "uae_pass")
    channel.setMethodCallHandler(this)
    binding = flutterPluginBinding;

    Log.d("intent","onAttachedToEngine")
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    flutterResult = result
    if(call.method=="set_up_environment")
    {
      CookieManager.getInstance().removeAllCookies { }
      CookieManager.getInstance().flush()

    }else if(call.method=="sign_in")
    {
      requestModel = UAEPassRequestModels.getAuthenticationRequestModel(this.activityPluginBinding.getActivity())
      getAccessToken(this.activityPluginBinding.getActivity(), requestModel, object : UAEPassAccessTokenCallback {
        override fun getToken(accessToken: String?, state: String, error: String?) {
            Log.d("intent","inside call back")
          if (error != null) {
            Log.d("intent","inside error")
            result.error("ERROR", error, null);
          } else {
            Log.d("intent","Sccess")
            Log.d("intent",accessToken!!)
             
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
    Log.d("intent","inside onNewIntent")
    handleIntent(intent)
    return false
  }
  private fun handleIntent(intent: Intent?) {
    if (intent != null && intent.data != null) {
      if ("myapp" == intent.data!!.scheme) {
        
        resume(intent.dataString)
      }
    }
  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    Log.d("intent","onDetachedFromEngine")
    channel.setMethodCallHandler(null)
  }
  
}
