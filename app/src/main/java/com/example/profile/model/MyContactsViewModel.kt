package com.example.profile.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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