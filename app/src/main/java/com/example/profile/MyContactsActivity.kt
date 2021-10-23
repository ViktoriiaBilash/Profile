package com.example.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profile.databinding.ActivityMyContactsBinding
import com.example.profile.model.ContactsService
import com.example.profile.model.MyViewModel

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerViewMyContactsItem

        //Creates ViewModelProvider - to store all activities ViewModel
     //   val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        //responsible for moving item
        recyclerView.layoutManager = LinearLayoutManager(this)

        val listContacts = ContactsService()
        recyclerView.adapter = ContactsAdapter(listContacts.getContacts())
    }

}