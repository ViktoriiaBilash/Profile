package com.example.profile.model

import java.util.*

class ContactsService {

    private var contacts = mutableListOf<Contact>()
//    private val listeners = mutableListOf<UsersL>()
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
        for (n in 0..20) {
            contacts.add(
                Contact(
                    names[n % names.size], "developer, exp $n years", "Beverly Hills, 9021$n", n
                )
            )
        }
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun deleteUser(contact: Contact) {
        contacts.remove(contact)
    }

    fun moveContact(contact: Contact, position: Int) {
        val index = contacts.indexOf(contact)
        if (index == -1) return
        val newIndex = index + position
        if (newIndex < 0 || newIndex >= contacts.size) return
        Collections.swap(contacts, index, newIndex)
    }
}