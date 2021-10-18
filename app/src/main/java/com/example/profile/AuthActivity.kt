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

import com.example.profile.utils.extensions.saveString

class AuthActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)

        setListeners()
        isAutologin()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val checkBox = binding.checkBoxRememberMe

        with(outState) {
            putString(Constants.USER_EMAIL, email)
            putString(Constants.USER_PASSWORD, password)
            putBoolean(Constants.REMEMBER_ME, checkBox.isChecked)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val email = savedInstanceState.getString(Constants.USER_EMAIL)
        val password = savedInstanceState.getString(Constants.USER_PASSWORD)
        val checkBox = savedInstanceState.getBoolean(Constants.REMEMBER_ME)

        with(binding) {
            editTextEmail.setText(email)
            editTextPassword.setText(password)
            checkBoxRememberMe.isChecked = checkBox
        }
    }

    private fun setListeners() {
        with(binding) {
            editTextEmail.doOnTextChanged { _, _, _, _ ->
                inputTextEmail.error = ""
            }
            editTextPassword.doOnTextChanged { _, _, _, _ ->
                inputTextPassword.error = ""
            }
            btnRegister.setOnClickListener {
                goToMyProfile()
            }
        }
    }

    private fun isAutologin() {
        if (sharedPreferences.contains(Constants.USER_EMAIL)) {
            userEmail = sharedPreferences.getString(Constants.USER_EMAIL, "").toString()
            startActivityMain()
        }
    }

    private fun goToMyProfile() {
        userEmail = binding.editTextEmail.text.toString()
        userPassword = binding.editTextPassword.text.toString()

        if (isValidEmail(userEmail) && isValidPassword(userPassword)) {
            rememberUser()
            startActivityMain()
            finish()
        } else {
            showErrors()
        }
    }

    private fun startActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        with(intent) {
            putExtra(Constants.USER_EMAIL, userEmail)
            putExtra(Constants.USER_NAME, parseEmail(userEmail))
        }
        startActivity(intent)
    }

    private fun rememberUser() {
        val checkBox = binding.checkBoxRememberMe
        if (checkBox.isChecked) {
            sharedPreferences.saveString(Constants.USER_EMAIL, userEmail)
        }
    }

    private fun showErrors() {
        val emailError = getString(R.string.auth_e_mail_error_text)
        val passwordError = getString(R.string.auth_password_error_text)
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



