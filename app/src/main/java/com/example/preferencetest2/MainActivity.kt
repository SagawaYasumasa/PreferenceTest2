package com.example.preferencetest2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.example.preferencetest2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    private lateinit var common : Common
    private lateinit var binding : ActivityMainBinding
    private lateinit var serverAddress : String         // WebApi Server address

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Common objects
        common= getApplication() as Common
        common.mainActivity = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity onCreate","onCreate")

        // Move to SubActivity
        binding.btnSettings.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        serverAddress =loadPreference(common.mainActivity)
        Log.d("MainActivity OnResume","serverAddress="+serverAddress)
    }
}
fun loadPreference(context:MainActivity) :String{
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val address = pref.getString("ADDRESS","")
    return (address.toString())
}
fun savePreference(context:MainActivity,address: String){
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = pref.edit()
    editor.putString("ADDRESS", address)
        .apply()
}

suspend fun testServerAddress(address:String, view:View): Boolean {
    var ret = false
    if (address.isBlank()) { ret = false } else {
        val scope = CoroutineScope(Dispatchers.Default)
        ret = scope.async { postEcho(address) }.await()
        Log.d("testServerAddress", "ret=" + ret)
    }
    if(ret) {
        val snackbar = Snackbar.make(view, "Connection is successful.".format(address), Snackbar.LENGTH_LONG)
        snackbar.show()
    } else {
        val snackbar = Snackbar.make(view, "Failed to connect to server.", Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    }
    return ret
}
