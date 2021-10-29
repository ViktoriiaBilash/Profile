package com.example.profile.model

data class Contact(
    val id : Int,
    val name : String,
    val career : String,
    val homeAddress : String,
    val num : Int
){
    val icon : String = "https://i.pravatar.cc/50?img=$num"
}