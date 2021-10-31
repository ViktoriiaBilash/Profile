package com.example.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.ActivityMyContactsBinding
import com.example.profile.model.Contact
import com.example.profile.model.MyContactsViewModelFactory
import com.example.profile.model.ContactsService
import com.example.profile.model.MyContactsViewModel
import java.util.*

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyContactsBinding
    private lateinit var recyclerView : RecyclerView

    /////////////////////////////////
    private var modelToBeUpdated: Stack<Contact> = Stack()////////
    private lateinit var adapter : MyContactsAdapter
    private val listener = object : ItemClickListener{
        override fun onDelete(model: Contact) {
            Log.e("AAAA", "fun onDelete")
            adapter.removeContact(model)
        }

        override fun onUpdate(position: Int, model: Contact) {
           modelToBeUpdated.add(model)
            Log.e("AAAA", "fun onUpdate")
            ///?????set the value
        }

        override fun addContact(model: Contact) {
            Log.e("AAAA", "faddContact")
            adapter.addContact(model)
        }

    }
/////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AAAA", "Activity created")
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("AAAA", "Activity set recycler")
        recyclerView = binding.recyclerViewMyContactsItem

        //responsible for moving item
        Log.e("AAAA", "Activity set recycler layoutManager")
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Creates ViewModelProvider - to store all activities ViewModel
        Log.e("AAAA", "Activity set VM")
        val viewModel = ViewModelProvider(this, MyContactsViewModelFactory(ContactsService()))
            .get(MyContactsViewModel::class.java)

        viewModel.listOutside.observe(this, Observer {
            Log.e("AAAA", "Activity set observe")
            adapter = MyContactsAdapter(it as MutableList<Contact>, listener)
            recyclerView.adapter = adapter
        })
        Log.e("AAAA", "Activity set listeners")
        setListeners()
    }

        private fun setListeners() {
            Log.e("AAAA", "Activity  setListeners")
            with(binding) {
                Log.e("AAAA", "Activity  setListeners button")
                  buttonAddContact.setOnClickListener {
                    goToAddingContact()
                }
                buttonBack.setOnClickListener{
                    Log.e("AAAA", "Activity  setListeners on Back")
                    onBackPressed()
                }
                buttonFind.setOnClickListener{
                    Log.e("AAAA", "Activity  setListeners find")
                    //will add
                }

            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
    private fun goToAddingContact() {
        Log.e("AAAA", "Activity goToAddingContact()")
        var dialog = AddContactFragment(listener)
        dialog.show(supportFragmentManager, AddContactFragment.TAG)
//
    }
}