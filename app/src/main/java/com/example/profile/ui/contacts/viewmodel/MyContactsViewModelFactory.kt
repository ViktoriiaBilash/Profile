package com.example.profile.ui.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profile.ui.contacts.model.ContactsService

class MyContactsViewModelFactory(private val contactsService: ContactsService):
ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyContactsViewModel(contactsService) as T
    }
}