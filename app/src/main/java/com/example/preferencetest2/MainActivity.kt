package com.example.preferencetest2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.preferencetest2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var common : Common
    private lateinit var instance : MainActivity        // applicationContext取得用
    private lateinit var binding : ActivityMainBinding
    private lateinit var serverAddress : String         // WebApi Server address


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Common objects
        common= getApplication() as Common
        common.mainActivity = this

        instance = this                                 // applicationContext取得用
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
        serverAddress =loadPreference(this.getInstance())
        Log.d("MainActivity OnResume","serverAddress="+serverAddress)
    }
    // applicationContext取得用
    public fun getInstance():MainActivity{
        return instance
    }
}
fun loadPreference(context:MainActivity) :String{
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
//        val pref = PreferenceManager.getDefaultSharedPreferences(this)
    val address = pref.getString("ADDRESS","")
    return (address.toString())
}
fun savePreference(context:MainActivity,address: String){
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = pref.edit()
    editor.putString("ADDRESS", address)
        .apply()
}

// dummy
public fun testServerAddress(address:String): Boolean {
    if (address.isNotBlank()) {
        return true
    } else {
        return false
    }
}
