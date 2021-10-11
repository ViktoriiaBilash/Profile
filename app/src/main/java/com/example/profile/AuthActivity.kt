package com.example.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.example.profile.databinding.ActivityAuthBinding

const val APP_PREF = "APP_PREFERENCES"
const val USER_EMAIL = "USER_EMAIL"
const val USER_NAME = "USER_NAME"
const val USER_PASSWORD = "USER_PASSWORD"
const val CHECK_BOX = "CHECK_BOX"

class AuthActivity : AppCompatActivity() {

    private lateinit var setting: SharedPreferences
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting = getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

        setListeners()
        checkAutologin()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val checkBox = binding.checkBoxRememberMe.isChecked

        outState.putString(USER_EMAIL, email)
        outState.putString(USER_PASSWORD, password)
        outState.putBoolean(CHECK_BOX, checkBox)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val email = savedInstanceState.get(USER_EMAIL)
        val password = savedInstanceState.get(USER_PASSWORD)
        val checkBox = savedInstanceState.get(CHECK_BOX)

        binding.editTextEmail.setText(email.toString())
        binding.editTextPassword.setText(password.toString())
        if (checkBox == true)
            binding.checkBoxRememberMe.isChecked
    }

    private fun setListeners() {
        with(binding){
            editTextEmail.doOnTextChanged { _, _, _, _ ->
                inputTextEmail.error = null
            }
            editTextPassword.doOnTextChanged { _, _, _, _ ->
               inputTextPassword.error = null
            }
            btnRegister.setOnClickListener {
                onClick()
            }
        }
    }

    private fun checkAutologin() {
        if (iaAutoLogin()) {
            userEmail = setting.getString(USER_EMAIL, "").toString()
            startActivityAuth()
        }
    }

    private fun iaAutoLogin(): Boolean {
        return setting.contains(USER_EMAIL)
    }

    private fun onClick() {
        userEmail = binding.editTextEmail.text.toString()
        userPassword = binding.editTextPassword.text.toString()

        if (isValidEmail(userEmail) && isValidPassword(userPassword)) {
            rememberUser()
            startActivityAuth()
            finish()
        } else {
            showError()
        }
    }

    private fun startActivityAuth() {
        val registerIntent = Intent(this, MainActivity::class.java)
        with(registerIntent) {
            putExtra(USER_EMAIL, userEmail)
            putExtra(USER_NAME, parseEmail(userEmail))
        }
        startActivity(registerIntent)
    }

    private fun rememberUser() {
        val checkBox = binding.checkBoxRememberMe
        if (checkBox.isChecked) {
            val editor = setting.edit()
            editor.putString(USER_EMAIL, userEmail).apply()
        }
    }

    private fun showError() {
        val emailError = getString(R.string.email_is_not_valid)
        val passwordError = getString(R.string.password_is_not_valid)
        if (!isValidEmail(userEmail)) {
            binding.inputTextEmail.error = emailError
        }
        if (!isValidPassword(userPassword)) {
            binding.inputTextPassword.error = passwordError
        }
    }

    private fun isValidPassword(userPassword: String): Boolean {
        val minLengthPass = 6
        val maxLengthPass = 60
        return userPassword.length in minLengthPass..maxLengthPass
    }

    private fun isValidEmail(email: String): Boolean {
        val requiredTag = "@"
        return requiredTag.toRegex().containsMatchIn(email)
    }

    private fun parseEmail(userEmail: String): String {
        val separator = userEmail.indexOf(".")

        var user = userEmail.replace(userEmail[0].toString(), userEmail[0].uppercase())
        user = user.replace(user[separator + 1].toString(), user[separator + 1].uppercase())
        user = user.replace(".", " ")

        return user.substringBefore('@')
    }
}



