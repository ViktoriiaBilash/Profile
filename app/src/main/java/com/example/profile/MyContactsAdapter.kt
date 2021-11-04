package com.example.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.ItemBinding
import com.example.profile.model.Contact
import com.example.profile.utils.extensions.loadImageWithGlide

class MyContactsAdapter(

    private var contacts: MutableList<Contact>,
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<MyContactsAdapter.ContactsViewHolder>() {

    private var context: Context? = null

    override fun getItemCount(): Int {
        return contacts.size
    }

    //used to create a new element of list
    //LayoutInflater creates view from layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(
            view, parent, false
        )
        val holder = ContactsViewHolder(binding)

        holder.binding.buttonDelete.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            val model = contacts[position]
            context = parent.context

            val newList: List<Contact> = ArrayList(contacts)
            listener.onDelete(model)
            updateList(newList, contacts)
        }
        return holder
    }

    //upload an item
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvCareer.text = contact.career

        if (contact.icon != null) {
            holder.binding.imgAvatar.loadImageWithGlide(contact.icon.toString())
        } else {
            holder.binding.imgAvatar.setImageResource(R.drawable.ic_contact_avatar)
        }
    }

    private fun updateList(oldList: List<Contact>, newList: List<Contact>) {
        val diffCallback = ContactsDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    class ContactsDiffCallback(
        private val oldList: List<Contact>,
        private val newList: List<Contact>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]== newList[newItemPosition]
        }
    }

    class ContactsViewHolder(
        val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}