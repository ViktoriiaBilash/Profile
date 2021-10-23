package com.example.profile.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel(private val contactsService: ContactsService) : ViewModel() {

    //could be change by ViewModel
    private val listInside = MutableLiveData<List<Contact>>()

    //for access view
    val listOutside: LiveData<List<Contact>> = listInside

    fun loadContacts() {

    }

    fun moveContact(contact: Contact, position: Int) {
        contactsService.moveContact(contact, position)
    }

    fun deleteContact(contact: Contact) {
        contactsService.deleteUser(contact)
    }
}