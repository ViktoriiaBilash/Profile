package com.example.profile.ui.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.databinding.ActivityMyContactsBinding
import com.example.profile.ui.contacts.adapter.ItemClickListener
import com.example.profile.ui.contacts.adapter.MyContactsAdapter
import com.example.profile.model.Contact
import com.example.profile.db.ContactsService
import com.example.profile.ui.contacts.viewmodel.MyContactsViewModel
import com.example.profile.ui.contacts.viewmodel.MyContactsViewModelFactory

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyContactsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: MyContactsAdapter
    private lateinit var viewModel: MyContactsViewModel
    private val listener = object : ItemClickListener {

        override fun onDelete(model: Contact) {
            viewModel.removeContact(model)
            Toast.makeText(baseContext, R.string.contact_removed, Toast.LENGTH_SHORT)
                .show()
        }

        override fun addContact(model: Contact) {
            viewModel.addContact(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MyContactsViewModelFactory(ContactsService()))
            .get(MyContactsViewModel::class.java)

        initRecycler()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.contactsListLiveData.observe(this, {
            contactsAdapter.submitList(it.toMutableList())
        })
    }

    private fun initRecycler() {
        recyclerView = binding.recyclerViewMyContactsItem
        recyclerView.layoutManager = LinearLayoutManager(this@MyContactsActivity)
        contactsAdapter = MyContactsAdapter(listener)
        recyclerView.adapter = contactsAdapter
    }


    private fun setListeners() {
        with(binding) {
            buttonAddContact.setOnClickListener {
                val dialog = AddContactDialog(listener)
                dialog.show(supportFragmentManager, AddContactDialog.TAG)
            }
            buttonBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}