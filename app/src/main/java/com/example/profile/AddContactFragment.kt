package com.example.profile

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.profile.databinding.FragmentAddContactBinding
import com.example.profile.model.Contact

class AddContactFragment (private val listener: ItemClickListener) : DialogFragment() {
    private lateinit var binding: FragmentAddContactBinding
    lateinit var newContact: Contact

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddContactBinding.inflate(layoutInflater)
        var view : View = binding.root

        binding.buttonSave.setOnClickListener{
            addNewContact()
            listener.addContact(newContact)
            dismiss()
        }
        return view
    }

    private fun addNewContact() {
        val userName = binding.editTextUserName.text.toString()
        val career = binding.editTextCareer.text.toString()
        val homeAddress = binding.editTextAddress.text.toString()
newContact = Contact(10, userName,career, homeAddress, 10)

    }

    override fun onDismiss(dialog: DialogInterface) {
        Log.e("AAAA", "onDismiss")
        super.onDismiss(dialog)
    }

    
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.getWindow()?.setLayout(width, height)
        }
    }
    companion object{
        //unique dialogs tag
        @JvmStatic val TAG = AddContactFragment::class.java.simpleName

    }    }