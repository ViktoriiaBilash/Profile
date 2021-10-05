package com.example.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.profile.databinding.ActivityAuthBinding
import com.google.android.material.textfield.TextInputEditText

const val APP_PREF = "APP_PREFERENCES"
const val USER_EMAIL = "USER_EMAIL"
const val USER_PASSWORD = "USER_PASSWORD"
const val USER_NAME = "USER_NAME"

class AuthActivity : AppCompatActivity() {

    private lateinit var setting: SharedPreferences
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var binding: ActivityAuthBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setting = getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

        setListeners()
        isAutoLogin()
    }

    override fun onStop() {
        super.onStop()
        binding.editTextTextEmailAddress.text = null
        binding.editTextTextPassword.text = null
    }

    private fun setListeners() {
        binding.editTextTextEmailAddress.afterChanged()
        binding.editTextTextPassword.afterChanged()
        binding.button3.setOnClickListener {
            onClick()
        }
    }

    private fun TextInputEditText.afterChanged() {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p: CharSequence?, start: Int, count: Int, end: Int) {
            }

            override fun onTextChanged(p: CharSequence?, start: Int, before: Int, count: Int) {
                binding.emailTextInputLayout.error = null
                binding.passwordTextInputLayout.error = null
            }

            override fun afterTextChanged(p: Editable?) {
            }
        })
    }

    private fun isAutoLogin() {
        if (autoLogin()) {
            userEmail = setting.getString(USER_EMAIL, "").toString()
            userPassword = setting.getString(USER_PASSWORD, "").toString()

            startActivityAuth()
        }
    }

    private fun autoLogin(): Boolean {
        return setting.contains(USER_EMAIL) && setting.contains(USER_PASSWORD)
    }

    fun onClick() {
        userEmail = binding.editTextTextEmailAddress.text.toString()
        userPassword = binding.editTextTextPassword.text.toString()

        if (isValidEmail(userEmail) && isValidPassword(userPassword)) {
            rememberUser()
            startActivityAuth()
        } else {
            showError()
        }
    }

    private fun startActivityAuth() {
        val registerIntent = Intent(this, MainActivity::class.java)

        registerIntent.putExtra(USER_EMAIL, userEmail)
            .putExtra(USER_NAME, parseEmail(userEmail))
            .putExtra(USER_PASSWORD, userPassword)

        startActivity(registerIntent)
    }

    private fun rememberUser() {
        val checkBox = binding.appCompatCheckBox

        if (checkBox.isChecked) {
            val editor = setting.edit()
            editor.putString(USER_EMAIL, userEmail)
                .putString(USER_PASSWORD, userPassword)
                .apply()
        }
    }

    private fun showError() {
        val emailError = context.getString(R.string.email_is_not_valid)
        val passwordError = context.getString(R.string.password_is_not_valid)
        if (!isValidEmail(userEmail)) {
            binding.emailTextInputLayout.error = emailError
        }
        if (!isValidPassword(userPassword)) {
            binding.passwordTextInputLayout.error = passwordError
        }
    }

    private fun isValidPassword(userPassword: String): Boolean {
        val minLengthPass = 6
        val maxLengthPass = 60
        return userPassword.length in minLengthPass..maxLengthPass
    }

    private fun isValidEmail(email: String): Boolean {
        val requiredTag = '@'
        return email.contains(requiredTag)
    }

    private fun parseEmail(userEmail: String): String {
        val separator = userEmail.indexOf(".")

        var user = userEmail.replace(userEmail[0].toString(), userEmail[0].uppercase())
        user = user.replace(user[separator + 1].toString(), user[separator + 1].uppercase())
        user = user.replace(".", " ")

        return user.substringBefore('@')
    }
}


