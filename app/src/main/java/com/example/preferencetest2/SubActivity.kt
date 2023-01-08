package com.example.preferencetest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.preference.PreferenceManager
import com.example.preferencetest2.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var common : Common
    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common = getApplication() as Common

        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_sub)

        // Edit Server Address
        val editServerAddress = findViewById<EditText>(R.id.editServerAddress)
        editServerAddress.setOnEditorActionListener{ _,actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                Log.d("editServerAddress","IME_ACTION_DONE")
                testServerAddress(editServerAddress.getText().toString())
            }
            return@setOnEditorActionListener true
        }

        // Test Server Address
        val btnTestServerAddress = findViewById<Button>(R.id.btnTestServerAddress)
        btnTestServerAddress.setOnClickListener{
            val _address = editServerAddress.getText().toString()
            testServerAddress(editServerAddress.getText().toString())
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

/*
    private fun loadPreference() :String{
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val address = pref.getString("ADDRESS","")
        return (address.toString())
    }
 */
/*
    private fun savePreference( address: String){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("ADDRESS", address)
            .apply()
    }

 */
}
