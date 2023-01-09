package com.example.preferencetest2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.preferencetest2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var common : Common
    private lateinit var binding : ActivityMainBinding

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
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
        binding.btnUpload.setOnClickListener {
            val _ret = common.webApiHeatmap.testServerAddress(common.webApiHeatmap.serverAddress)
            Log.d("btnUpload","_ret="+_ret.toString())

            if(_ret) {
                val snackbar = Snackbar.make(it, "upload", Snackbar.LENGTH_LONG)
                snackbar.show()
                // upload process
            } else {
                val snackbar = Snackbar.make(it, "Failed to connect to server.", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity","onResume")
        common.webApiHeatmap.loadServerAddressFromPreference()
    }
}