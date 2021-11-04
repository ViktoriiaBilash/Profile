package com.example.profile

import com.example.profile.model.Contact

interface ItemClickListener {
    fun onDelete(model: Contact)

    fun addContact(model: Contact)
}