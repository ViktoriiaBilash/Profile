package com.example.profile.ui.contacts.model

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
        for (n in 0..20) {
            contacts.add(
                Contact(
                    n,
                    names[n % names.size],
                    "developer, exp $n years", "Beverly Hills, 9021$n"
                )
            )
            contacts[n].icon = "https://i.pravatar.cc/50?img=$n"
        }
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun removeContact(model: Contact) {
        contacts.remove(model)
    }

    fun addContact(model: Contact) {
        contacts.add(model)
    }
}