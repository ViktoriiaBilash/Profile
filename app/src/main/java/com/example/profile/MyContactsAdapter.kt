package com.example.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.ItemBinding
import com.example.profile.model.Contact
import com.example.profile.utils.extensions.loadImageWithGlide
import com.google.android.material.button.MaterialButton

//adapter contains list of contacts (users)
class MyContactsAdapter(
    private val contacts: MutableList<Contact>,
    private val listener: ItemClickListener) :
    RecyclerView.Adapter<MyContactsAdapter.ContactsViewHolder>() {

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

            listener.onDelete(model)
        }
        //return ContactsViewHolder(binding)
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
        val position = contacts.indexOf(model)
        contacts.remove(model)
        notifyItemRemoved(position)
    }


    fun updateProduct(model: Contact?) {
        Log.e("AAAA", "Adapter update")
        if (model == null) return // we cannot update the value because it is null

        for (item in contacts) {
            // search by id
            if (item.id == model.id) {
                val position = contacts.indexOf(model)
                contacts[position] = model
                notifyItemChanged(position)
                break // we don't need to continue anymore
            }
        }
    }

    //contains date
    class ContactsViewHolder(
       val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        private val buttonDelete : MaterialButton = binding.buttonDelete

        init {
            Log.e("AAAA", "ViewHolder create")
           // buttonDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.e("AAAA", "ViewHolder onClick")

        }
    }
}