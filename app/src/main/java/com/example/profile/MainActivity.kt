package com.example.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user = intent.getStringExtra("user name")
        val out = findViewById<AppCompatTextView>(R.id.name_text_view)
        out.setText(user)
    }
}