package com.example.profile.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.databinding.ItemBinding
import com.example.profile.model.Contact
import com.example.profile.utils.extensions.loadImageWithGlide

class MyContactsAdapter(

    private val listener: ItemClickListener
) :
    ListAdapter<Contact, MyContactsAdapter.ContactsViewHolder>(ContactsDiffCallback()) {


    //used to create a new element of list
    //LayoutInflater creates view from layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(
            view, parent, false
        )
        return ContactsViewHolder(binding, listener)
    }

    //upload an item
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    class ContactsDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    class ContactsViewHolder(
        private val binding: ItemBinding,
        private val listener: ItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(contact: Contact) {
            binding.tvName.text = contact.name
            binding.tvCareer.text = contact.career

            if (contact.icon != null) {
                binding.imgAvatar.loadImageWithGlide(contact.icon.toString())
            } else {
                binding.imgAvatar.setImageResource(R.drawable.ic_contact_avatar)
            }

            binding.buttonDelete.setOnClickListener {
                listener.onDelete(contact)
            }
        }
    }
}