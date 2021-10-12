package com.example.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.profile.databinding.ActivityAuthBinding
import com.example.profile.utils.Constants

class AuthActivity : AppCompatActivity() {

    private lateinit var setting: SharedPreferences
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting = getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)

        setListeners()
        checkAutologin()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val checkBox = binding.checkBoxRememberMe.isChecked

        with(outState) {
            putString(Constants.USER_EMAIL, email)
            putString(Constants.USER_PASSWORD, password)
            putBoolean(Constants.CHECK_BOX, checkBox)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val email = savedInstanceState.get(Constants.USER_EMAIL)
        val password = savedInstanceState.get(Constants.USER_PASSWORD)
        val checkBox = savedInstanceState.get(Constants.CHECK_BOX)

        with(binding) {
            editTextEmail.setText(email.toString())
            editTextPassword.setText(password.toString())
            checkBoxRememberMe.isChecked = checkBox as Boolean
        }
    }

    private fun setListeners() {
        with(binding) {
            editTextEmail.doOnTextChanged { _, _, _, _ ->
                inputTextEmail.error = null
            }
            editTextPassword.doOnTextChanged { _, _, _, _ ->
                inputTextPassword.error = null
            }
            btnRegister.setOnClickListener {
                goToMyProfile()
            }
        }
    }

    private fun checkAutologin() {
        if (isAutoLogin()) {
            userEmail = setting.getString(Constants.USER_EMAIL, "").toString()
            startActivityAuth()
        }
    }

    private fun isAutoLogin(): Boolean {
        return setting.contains(Constants.USER_EMAIL)
    }

    private fun goToMyProfile() {
        userEmail = binding.editTextEmail.text.toString()
        userPassword = binding.editTextPassword.text.toString()

        if (isValidEmail(userEmail) && isValidPassword(userPassword)) {
            rememberUser()
            startActivityAuth()
            finish()
        } else {
            showErrors()
        }
    }

    private fun startActivityAuth() {
        val registerIntent = Intent(this, MainActivity::class.java)
        with(registerIntent) {
            putExtra(Constants.USER_EMAIL, userEmail)
            putExtra(Constants.USER_NAME, parseEmail(userEmail))
        }
        startActivity(registerIntent)
    }

    private fun rememberUser() {
        val checkBox = binding.checkBoxRememberMe
        if (checkBox.isChecked) {
            val editor = setting.edit()
            editor.putString(Constants.USER_EMAIL, userEmail).apply()
        }
    }

    private fun showErrors() {
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
        return userPassword.length in Constants.MIN_LENGTH_PASSWORD..Constants.MAX_LENGTH_PASSWORD
    }

    private fun isValidEmail(email: String): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    private fun parseEmail(userEmail: String): String {
        val separator = userEmail.indexOf(".")

        var user = userEmail.replaceFirst(userEmail[0].toString(), userEmail[0].uppercase())
        user = user.replaceFirst(user[separator + 1].toString(), user[separator + 1].uppercase())
        user = user.replaceFirst(".", " ")
        return user.substringBefore('@')
    }
}



