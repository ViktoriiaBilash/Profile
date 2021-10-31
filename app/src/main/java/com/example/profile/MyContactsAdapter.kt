package com.example.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.FragmentAddContactBinding
import com.example.profile.databinding.ItemBinding
import com.example.profile.model.Contact
import com.example.profile.utils.extensions.loadImageWithGlide
import com.google.android.material.button.MaterialButton

//adapter contains list of contacts (users)
class MyContactsAdapter(

    private var contacts: MutableList<Contact>,
    private val listener: ItemClickListener) :
    RecyclerView.Adapter<MyContactsAdapter.ContactsViewHolder>() {

    private var context: Context? = null

    init {
        Log.e("AAAA", "Adapter created")
    }
    //how many elements in the list
    override fun getItemCount(): Int {
        Log.e("AAAA", "Adapter get itemCount")
        return contacts.size
    }

    //used to create a new element of list, save date about it
    //LayoutInflater creates view from layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        Log.e("AAAA", "Adapter on Create view holder")
        val view = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(
            view, parent, false
        )
///////////////////////////////////////////////////////
        val holder = ContactsViewHolder(binding)
        holder.itemView.setOnClickListener{
            val position = holder.absoluteAdapterPosition
            val model = contacts[position]

            listener.onUpdate(position, model)
        }
        holder.binding.buttonDelete.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            val model = contacts[position]
context = parent.context
            listener.onDelete(model)
        }

        return holder
    }

    //upload an item
    //holder contains all views from item_list
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        Log.e("AAAA", "Adapter on Bind view holder")
        val contact = contacts[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvCareer.text = contact.career
        holder.binding.imgAvatar

        val icon : ImageView = holder.binding.imgAvatar
        icon.loadImageWithGlide(contact.icon)

    }

    fun removeContact(model: Contact){
        Log.e("AAAA", "Adapter remove")
        val newList : List<Contact> = contacts
        contacts = ArrayList(contacts)
        contacts.remove(model)
        updateList(newList, contacts)

        Toast.makeText(context, context?.getString(R.string.contact_removed), Toast.LENGTH_SHORT).show()
    }


    fun addContact(model: Contact){
        Log.e("AAAA", "Adapter addContact")
        val newList : List<Contact> = contacts
        contacts = ArrayList(contacts)
        contacts.add(model)
        updateList(newList, contacts)
    }

    private fun updateList(oldList : List<Contact>, newList : List<Contact>) {

        Log.e("AAAA", "Adapter update")

        val diffCallback = ContactsDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        //contacts = newList as MutableList<Contact>
        diffResult.dispatchUpdatesTo(this)
    }

    class ContactsDiffCallback(
        private val oldList : List<Contact>,
        private val newList : List<Contact>
    ) : DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            Log.e("AAAA", "DiffCallback getOldListSize ")
            return oldList.size
        }

        override fun getNewListSize(): Int {
            Log.e("AAAA", "DiffCallback getnewListSize ")
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            Log.e("AAAA", "DiffCallbackareItemsTheSame")
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            Log.e("AAAA", "DiffCallbackare areContentsTheSame")
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }

    }
    //contains date
    class ContactsViewHolder(
       val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        override fun onClick(p0: View?) {
            Log.e("AAAA", "ViewHolder onClick")

        }
    }
}