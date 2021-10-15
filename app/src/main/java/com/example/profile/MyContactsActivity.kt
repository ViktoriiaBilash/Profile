package com.example.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.profile.databinding.ActivityMyContactsBinding

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}