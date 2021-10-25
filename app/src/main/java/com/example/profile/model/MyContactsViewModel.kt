package com.example.profile.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyContactsViewModel(private val contactsService: ContactsService) : ViewModel() {

    //could be change by ViewModel
    private val listInside = MutableLiveData<List<Contact>>()

    //for access view
    val listOutside: LiveData<List<Contact>> = listInside

    init {
        Log.e("AAAA", "VM created")
        loadContacts()
    }

    override fun onCleared() {
        Log.e("AAAA", "VM cleared")
        super.onCleared()
    }
    private fun loadContacts() {
        listInside.postValue(contactsService.getContacts())
    }
//
//    fun moveContact(contact: Contact, position: Int) {
//        contactsService.moveContact(contact, position)
//    }
//
//    fun deleteContact(contact: Contact) {
//        contactsService.deleteUser(contact)
//    }
}