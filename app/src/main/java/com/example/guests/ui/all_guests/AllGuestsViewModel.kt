package com.example.guests.ui.all_guests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllGuestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is all guests Fragment"
    }
    val text: LiveData<String> = _text
}