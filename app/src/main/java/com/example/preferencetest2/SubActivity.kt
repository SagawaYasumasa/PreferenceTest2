package com.example.preferencetest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.preferencetest2.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    private lateinit var tilte : TitleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_sub)

        val intent = getIntent()
        val myName = intent.getStringExtra("keyName")

        val tvUri = findViewById<TextView>(R.id.tvUri)
        Log.d("SubActivity_onCreate","myName" + myName)
        tvUri.text = myName
    }

    override fun onResume() {
        super.onResume()
        Log.d("SubActivity","onResume")
    }
}