package com.example.preferencetest2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.example.preferencetest2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var  serverUri : String? = null
    private lateinit var editUri : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvTextView = findViewById<TextView>(R.id.tvTextView)
        editUri = findViewById<EditText>(R.id.editUri)

        serverUri= editUri.getText().toString()
        Log.d("onCreate","serverUri=" + serverUri.toString())

        val btnDisplayUri = findViewById<Button>(R.id.btnDisplayUri)
        btnDisplayUri.setOnClickListener{
            val _address = editUri.getText().toString()
            if( checkApiUri(_address)) {
                tvTextView.text = _address
                savePreference(_address)
            }
        }

        binding.btnSettings.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("keyName","Yasumasa")
            startActivity(intent)
        }

        editUri.setOnEditorActionListener{ _,actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                tvTextView.text=editUri.getText().toString()
            }
            return@setOnEditorActionListener true
        }

    }
    override fun onResume() {
        super.onResume()
        editUri.setText(loadPreference())
    }

    fun savePreference( address: String){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("ADDRESS", address)
            .apply()

    }
    fun loadPreference() :String{
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val address = pref.getString("ADDRESS","")
        return (address.toString())
    }
}
// dummy
public fun checkApiUri(uri:String): Boolean {
    if (uri.isNotBlank()) {
        return true
    } else {
        return false
    }
}