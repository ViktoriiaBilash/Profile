package com.example.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profile.databinding.ActivityMyContactsBinding
import com.example.profile.model.Contact
import com.example.profile.model.MyContactsViewModelFactory
import com.example.profile.model.ContactsService
import com.example.profile.model.MyContactsViewModel

class MyContactsActivity : AppCompatActivity() {

    private lateinit var listContacts : List <Contact>
    private lateinit var binding: ActivityMyContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AAAA", "Activity created")
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerViewMyContactsItem

        //responsible for moving item
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Creates ViewModelProvider - to store all activities ViewModel
        val viewModel = ViewModelProvider(this, MyContactsViewModelFactory(ContactsService()))
            .get(MyContactsViewModel::class.java)

        viewModel.listOutside.observe(this, Observer {
            recyclerView.adapter = MyContactsAdapter(it)
        })

    }

}