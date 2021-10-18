package com.example.profile.utils.extensions

import android.content.SharedPreferences

fun SharedPreferences.saveString(key: String, value: String){
    val edit = this.edit()
    edit.putString(key, value)
    edit.apply()
}

fun SharedPreferences.getString(key: String) : String{
    return this.getString(key)
}

fun SharedPreferences.clear(){
    val editor = this.edit()
    editor.clear().apply()
}