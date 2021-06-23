package com.example.guests.ui.guests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guests.constants.GuestConstants
import com.example.guests.databinding.ActivityGuestFormBinding

class GuestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel

    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSaveGuestsData.setOnClickListener { setListeners() }
        observeGuest()
        loadData()

    }

    //carrega dados do bundle
    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    private fun setListeners() {
        val name = binding.inputName.text.toString()
        val presence = binding.presence.isChecked

        mViewModel.saveDataGuest(mGuestId, name, presence)
    }

    private fun observeGuest() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        mViewModel.guest.observe(this, Observer {
            with(binding) {
                inputName.setText(it.name)
                if (it.presence) {
                    presence.isChecked = true
                } else {
                    absent.isChecked = true
                }
            }
        })
    }

}