package com.example.profile.ui.contacts

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.profile.databinding.DialogAddContactBinding
import com.example.profile.ui.contacts.adapter.ItemClickListener
import com.example.profile.model.Contact

class AddContactDialog(private val listener: ItemClickListener) : DialogFragment() {

    private lateinit var binding: DialogAddContactBinding
    private lateinit var newContact: Contact

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogAddContactBinding.inflate(layoutInflater)
        val view = binding.root

        with(binding) {
            buttonSave.setOnClickListener {
                addNewContact()
                listener.addContact(newContact)
                dismiss()
            }
            buttonBack.setOnClickListener {
                dismiss()
            }
        }
        return view
    }

    private fun addNewContact() {
        val userName = binding.editTextUserName.text.toString()
        val career = binding.editTextCareer.text.toString()
        val homeAddress = binding.editTextAddress.text.toString()
        newContact = Contact(null, userName, career, homeAddress)
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    companion object {
        //unique dialogs tag
        @JvmStatic
        val TAG: String = AddContactDialog::class.java.simpleName
    }
}