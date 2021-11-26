package com.example.profile.model

data class Contact(
    var id: Int?,
    var name: String,
    var career: String,
    var homeAddress: String,
    var icon: String? = null
) {
}