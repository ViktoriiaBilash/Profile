package com.example.profile.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyContactsViewModelFactory(private val contactsService: ContactsService):
ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyContactsViewModel(contactsService) as T
    }
}