package com.example.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profile.databinding.ItemBinding
import com.example.profile.model.Contact

//adapter contains list of contacts (users)
class ContactsAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    //how many elements in the list
    override fun getItemCount(): Int {
        return contacts.size
    }

    //used to create a new element of list, save date about it
    //LayoutInflater creates view from layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(
            view, parent, false
        )
        return ContactsViewHolder(binding)
    }

    //upload an item
    //holder contains all views from item_list
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvCareer.text = contact.career
        Glide.with(holder.itemView.context)
            .load(contact.icon)
            .circleCrop()
            .into(holder.binding.imgAvatar)

    }

    //contains date
    class ContactsViewHolder(
       val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
    }
}