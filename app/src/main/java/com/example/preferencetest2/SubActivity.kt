package com.example.preferencetest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.preferencetest2.databinding.ActivitySubBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            val _ret = runBlocking() {
                testServerAddress(
                    editServerAddress.getText().toString(),
                    findViewById(R.id.layout)
                )
            }
        }
    }
    override fun onPause(){
        super.onPause()
        Log.d("SubActivity","onPause")
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)
        savePreference(common.mainActivity,editServerAddress.getText().toString())
    }
    override fun onResume() {
        super.onResume()
        Log.d("SubActivity","onResume")
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)
        editServerAddress.setText(loadPreference(common.mainActivity))
    }
}