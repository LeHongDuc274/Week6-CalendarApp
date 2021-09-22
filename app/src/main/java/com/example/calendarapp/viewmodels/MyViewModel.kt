package com.example.calendarapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var _startDay = MutableLiveData<Int>(1)
    private val startDay : LiveData<Int> = _startDay

}