package com.mvpapps.uae_pass

import ae.sdg.libraryuaepass.*
import ae.sdg.libraryuaepass.UAEPassController.getAccessToken
import ae.sdg.libraryuaepass.UAEPassController.resume
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

// create a class that implements the PluginRegistry.NewIntentListener interface
 


/** UaePassPlugin */
class UaePassPlugin: FlutterPlugin, MethodCallHandler, ActivityAware,PluginRegistry.NewIntentListener{

  private lateinit var channel : MethodChannel
  private lateinit var requestModel: UAEPassAccessTokenRequestModel



  private var activity: Activity? = null
  private lateinit var result: Result


  override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
     Log.d("intent","onAttachedToActivity")
    if(activity==null)
     activity = binding.activity
    binding.addOnNewIntentListener(this)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    Log.d("intent","onDetachedFromActivityForConfigChanges")
  }

  override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {
     Log.d("intent","onReattachedToActivityForConfigChanges")
   activity =binding.activity
    binding.addOnNewIntentListener(this)

  }

  override fun onDetachedFromActivity() {
    Log.d("intent","onDetachedFromActivity")
    activity = null
  }


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "uae_pass")
    channel.setMethodCallHandler(this)

    Log.d("intent","onAttachedToEngine")
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    this.result = result
    if(call.method=="set_up_environment")
    {
      CookieManager.getInstance().removeAllCookies { }
      CookieManager.getInstance().flush()

    }else if(call.method=="sign_in")
    {
      Log.d("intent","signIn")
      requestModel = UAEPassRequestModels.getAuthenticationRequestModel(activity!!)
      getAccessToken(activity!!, requestModel, object : UAEPassAccessTokenCallback {
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
