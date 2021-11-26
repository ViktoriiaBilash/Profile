package com.example.profile.ui.contacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profile.model.Contact
import com.example.profile.db.ContactsService

class MyContactsViewModel(private val contactsService: ContactsService) : ViewModel() {

    val contactsListLiveData = MutableLiveData<MutableList<Contact>>()

    init {
        loadContacts()
    }

    fun removeContact(model: Contact) {
        contactsListLiveData.value?.remove(model)
        contactsListLiveData.value = contactsListLiveData.value
    }

    fun addContact(model: Contact) {
        model.id = contactsService.getContacts().size
        contactsListLiveData.value?.add(model)
        contactsListLiveData.value = contactsListLiveData.value
    }

    private fun loadContacts() {
        contactsListLiveData.value = contactsService.getContacts().toMutableList()
    }
}