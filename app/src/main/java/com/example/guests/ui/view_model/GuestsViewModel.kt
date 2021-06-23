package com.example.guests.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guests.constants.GuestConstants
import com.example.guests.service.model.GuestModel

import com.example.guests.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun loadGuests(filter: Int) {
        when (filter) {
            GuestConstants.FILTER.EMPTY -> {
                mGuestList.value = mGuestRepository.getAll()
            }
            GuestConstants.FILTER.PRESENT -> {
                mGuestList.value = mGuestRepository.getPresent()
            }
            else -> {
                mGuestList.value = mGuestRepository.getAbsent()
            }
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}