package com.example.guests.ui.guests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestFormViewModel : ViewModel() {

    private val mGuestRepository: GuestRepository = GuestRepository()

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest //será observado e não pode ser alterado de fora da ViewModel

    fun saveDataGuest(name: String, presence: Boolean) {
        val guest = GuestModel(name, presence)
        mGuestRepository.save(guest)
    }

}