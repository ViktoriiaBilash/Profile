package com.example.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.profile.databinding.ActivityMainBinding
import com.example.profile.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var setting: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting = getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val user = intent.getStringExtra(Constants.USER_NAME)
        binding.tvName.text = user

        setListeners()
    }

    private fun setListeners() {
        binding.btnViewContacts.setOnClickListener{
            goToMyContacts()
        }
    }

//    private fun goToMyContacts(java: Class<MyContactsActivity>) {
//val intent = Intent(this, java)
//        startActivity(intent)
//        finish()
//    }

    private fun goToMyContacts() {
        val intent = Intent(this, MyContactsActivity::class.java)
        startActivity(intent)
//        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val editor = setting.edit()
        editor.clear().apply()

        val authIntent = Intent(this, AuthActivity::class.java)
        startActivity(authIntent)
    }
}