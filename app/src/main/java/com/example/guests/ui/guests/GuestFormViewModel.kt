package com.example.guests.ui.guests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest //será observado e não pode ser alterado de fora da ViewModel

    fun saveDataGuest(name: String, presence: Boolean) {
        val guest = GuestModel(name = name, presence = presence)

        mSaveGuest.value = mGuestRepository.save(guest)
    }

}