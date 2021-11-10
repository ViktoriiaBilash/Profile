package com.example.profile.ui.contacts.adapter

import com.example.profile.ui.contacts.model.Contact

interface ItemClickListener {
    fun onDelete(model: Contact)

    fun addContact(model: Contact)
}