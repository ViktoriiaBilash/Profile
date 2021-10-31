package com.example.profile.model

import android.util.Log

class ContactsService {

    private var contacts = mutableListOf<Contact>()

    private var names = mutableListOf(
        "Adams",
        "Baker",
        "Clark",
        "Davis",
        "Evans",
        "Frank",
        "Russell",
        "Smith",
        "Bobby",
        "Ethan"
    )

    init {
        Log.e("AAAA", "ContactsService created")
        for (n in 0..2) {
            contacts.add(
                Contact(n,
                    names[n % names.size],
                    "developer, exp $n years", "Beverly Hills, 9021$n", n
                )
            )
        }
    }

    fun getContacts(): List<Contact> {
        Log.e("AAAA", "ContactsService get contacts")
        return contacts
    }

}