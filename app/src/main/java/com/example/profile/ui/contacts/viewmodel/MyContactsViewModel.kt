package com.example.profile.ui.contacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profile.ui.contacts.model.Contact
import com.example.profile.ui.contacts.model.ContactsService

class MyContactsViewModel(private val contactsService: ContactsService) : ViewModel() {

    private var listInside = MutableLiveData<List<Contact>>()
    val listOutside: LiveData<List<Contact>> = listInside

    init {
        loadContacts()
    }

    private fun loadContacts() {
        listInside.postValue(contactsService.getContacts())
    }

    fun removeContact(model: Contact) {
        contactsService.removeContact(model)
        listInside.postValue(contactsService.getContacts())
    }

    fun addContact(model: Contact) {
        model.id = contactsService.getContacts().size
        contactsService.addContact(model)
        listInside.postValue(contactsService.getContacts())
    }
}