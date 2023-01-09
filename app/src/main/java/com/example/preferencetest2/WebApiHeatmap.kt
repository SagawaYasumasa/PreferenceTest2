package com.example.preferencetest2

import android.util.Log
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class WebApiHeatmap(mainActivity: MainActivity) {
    companion object {
        const val SERVER_ADDRESS = "SERVER_ADDRESS"     // preference key
        const val FUNCTION_ECHO = "/echo.php"
    }
    private val mainActivity = mainActivity
    var serverAddress = ""         // Web API server address ex. "http://hoge.com/fuga/aho"
    init{
        // constructor
    }
    fun loadServerAddressFromPreference() : String{
        Log.d("loadServerAddressFromPreference","IN")
        val _pref = PreferenceManager.getDefaultSharedPreferences(mainActivity)
        serverAddress = _pref.getString(SERVER_ADDRESS,"") ?: ""
        return serverAddress
    }
    fun saveServerAddressToPreference(address : String ){
        Log.d("saveServerAddressFromPreference","IN")
        val _pref = PreferenceManager.getDefaultSharedPreferences(mainActivity)
        val editor = _pref.edit()
        editor.putString(SERVER_ADDRESS, address)
            .apply()
    }
    fun testServerAddress(address : String): Boolean {
        Log.d("testServerAddress","address="+address)
        return runBlocking(){callerPostEcho(address)}
    }
    suspend fun callerPostEcho(address : String) : Boolean{
        val scope = CoroutineScope(Dispatchers.Default)
        val _ret = scope.async { postEcho(address) }.await()
        return _ret
    }
     private fun postEcho(address : String): Boolean {
        var echoResult = false
        val url = address  + FUNCTION_ECHO
        val client: OkHttpClient = OkHttpClient.Builder().build()
        val json = "[{\"TEST\",\"SUCCESS\"}]"

        Log.d("postEcho","url="+url)
        // post
        try {
            val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            val request: Request = Request.Builder().url(url).post(postBody).build()
            val response = client.newCall(request).execute()
            // getResult
            val result = response.body()?.string() ?: ""
            Log.d("postEcho result",result)
            echoResult = (json == result)       // if send json == receive json then TRUE
            response.close()
        }
        catch ( e: Exception) {
            Log.d("postEcho",e.toString())
            echoResult = false
        }
        return echoResult
    }
}