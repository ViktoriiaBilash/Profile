package com.example.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class AuthActivity : AppCompatActivity() {

    lateinit var userEmail : String
    lateinit var userPassword: String
    lateinit var setting : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting = getSharedPreferences("app_users", Context.MODE_PRIVATE)
        if (autoLogin()){
            userEmail = setting.getString("userEmail", "").toString()
            userPassword = setting.getString("password", "").toString()
            startActivityAuth(userEmail, userPassword)
        }
        setContentView(R.layout.activity_auth)
    }

    private fun autoLogin(): Boolean {
return setting.contains("userEmail") && setting.contains("password")
    }

    fun onClick(view: View){
        userEmail = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress).text.toString()
        userPassword = findViewById<TextInputEditText>(R.id.editTextTextPassword).text.toString()
        if(isValidEmail(userEmail) && isValidPassword(userPassword)){
            rememberUser(userEmail, userPassword)
            startActivityAuth(userEmail, userPassword)
        } else{
            showError(userEmail, userPassword)
        }
    }

    private fun startActivityAuth(userEmail: String, userPassword: String) {
        val registerIntent = Intent(this, MainActivity::class.java)
        registerIntent.putExtra("e-mail", userEmail)
        registerIntent.putExtra("user name", parseEmail(userEmail))
        registerIntent.putExtra("password", userPassword)
        startActivity(registerIntent)
    }

    private fun rememberUser(userEmail: String, userPassword: String) {
        val checkBox = findViewById<CheckBox>(R.id.appCompatCheckBox)
            if (checkBox.isChecked) {
               val editor = setting.edit()
                editor.putString("userEmail", userEmail)
                editor.putString("password", userPassword)
                editor.apply()
        }
    }

    private fun showError(userEmail: String, userPassword: String) {
        val errorUserEmail = findViewById<TextInputLayout>(R.id.emailTextInputLayout)
        val errorUserPassword = findViewById<TextInputLayout>(R.id.passwordTextInputLayout)

        if(!isValidEmail(userEmail)){
            errorUserEmail.hint = "Email is not valid"

        } else{
            errorUserPassword.hint = "Passwords must contain more than 6 characters"
        }
    }

    private fun isValidPassword(userPassword: String) : Boolean{
        val minLengthPassword = 6
        return userPassword.length>=minLengthPassword
    }

    private fun isValidEmail(email : String) : Boolean{
        val requiredTag = '@'
        return email.contains(requiredTag)
    }

    private fun parseEmail(userEmail: String) : String{
        return userEmail.substringBefore('@')
    }

}
