package com.example.preferencetest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.preferencetest2.databinding.ActivitySubBinding
import com.google.android.material.snackbar.Snackbar

class SubActivity : AppCompatActivity() {
    private lateinit var common : Common
    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common = getApplication() as Common

        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Edit Server Address
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)

        // Test Server Address
        val btnTestServerAddress = findViewById<Button>(R.id.btnTestServerAddress)
        btnTestServerAddress.setOnClickListener {
            val _ret = common.webApiHeatmap.testServerAddress(editServerAddress.text.toString())
            if(_ret) {
                val snackbar = Snackbar.make(it, "Connection is successful.", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(it, "Failed to connect to server.", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
    override fun onPause(){
        super.onPause()
        Log.d("SubActivity","onPause")
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)
        common.webApiHeatmap.saveServerAddressToPreference(editServerAddress.getText().toString())
    }
    override fun onResume() {
        super.onResume()
        Log.d("SubActivity","onResume")
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)
        editServerAddress.setText(common.webApiHeatmap.loadServerAddressFromPreference())
    }
}