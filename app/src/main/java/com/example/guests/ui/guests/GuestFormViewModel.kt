package com.example.guests.ui.guests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest //será observado e não pode ser alterado de fora da ViewModel

    private var mGuest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = mGuest

    fun saveDataGuest(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel().apply {
            this.id = id
            this.name = name
            this.presence = presence
        }

        if (id == 0) {
            mSaveGuest.value = mGuestRepository.save(guest)
        } else {
            mSaveGuest.value = mGuestRepository.update(guest)
        }
    }

    fun load(id: Int) {
        mGuest.value = mGuestRepository.get(id)
    }

}