package com.example.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
    
    fun onClick(view: View){
val registerIntent = Intent(this, MainActivity::class.java)
        startActivity(registerIntent)
    }
}