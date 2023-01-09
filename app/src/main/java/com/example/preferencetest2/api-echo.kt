package com.example.preferencetest2

import android.util.Log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

// ワーカースレッド or coroutineで実行すること
fun postEcho(address : String): Boolean {
    var echoResult = false
    val url = address  + "/echo.php"
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
        echoResult = (json == result)
        response.close()
    }
    catch ( e: Exception) {
        Log.d("postEcho",e.toString())
        echoResult = false
    }
    return echoResult
}
/*
https://re-engines.com/2021/03/12/android-11%E3%81%A7deprecated%E3%81%AB%E3%81%AA%E3%81%A3%E3%81%9Fasynctask%E5%AF%BE%E5%BF%9Ckotlin%E7%B7%A8/
 */
