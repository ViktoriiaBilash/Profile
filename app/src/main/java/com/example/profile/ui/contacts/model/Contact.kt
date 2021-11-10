package com.example.profile.ui.contacts.model

data class Contact(
    var id: Int?,
    var name: String,
    var career: String,
    var homeAddress: String,
) {
    var icon: String? = null
}