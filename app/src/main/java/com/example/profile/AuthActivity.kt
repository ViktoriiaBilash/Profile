package com.example.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
    
    fun onClick(view: View){
        val registerIntent = Intent(this, MainActivity::class.java)

        val userEmail = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress).text.toString()
        val userPassword = findViewById<TextInputEditText>(R.id.editTextTextPassword).text.toString()

        if(isValidEmail(userEmail) && isValidPassword(userPassword)){
            registerIntent.putExtra("e-mail", userEmail)
            registerIntent.putExtra("user name", parseEmail(userEmail))
            registerIntent.putExtra("password", userPassword)

            startActivity(registerIntent)
        } else{
            showError(userEmail, userPassword)
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
        var minLengthPassword = 6
        return userPassword.length>=minLengthPassword
    }

    private fun isValidEmail(email : String) : Boolean{
        var requiredTag = '@'
        return email.contains(requiredTag)
    }

    private fun parseEmail(userEmail: String) : String{
        return userEmail.substringBefore('@')
    }

}
