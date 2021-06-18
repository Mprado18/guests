package com.example.guests.ui.guests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guests.databinding.ActivityGuestFormBinding

class GuestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observeGuest()
    }

    private fun setListeners() {

        val name = binding.nameGuest.text.toString()
        val presence = binding.presence.isChecked

        binding.buttonSaveGuestsData.setOnClickListener { mViewModel.saveDataGuest(name, presence) }
    }

    private fun observeGuest() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucess", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

}