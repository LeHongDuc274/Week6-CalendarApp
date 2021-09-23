package com.example.calendarapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var _startDay = MutableLiveData<Int>(1)
    private val startDay : LiveData<Int> = _startDay
    var _otherDayChecked = MutableLiveData<Int>(-1)
    private val otherDayChecked: LiveData<Int> = _otherDayChecked
    var _otherMonthChecked = MutableLiveData<Int>(-1)
    var _otherYearChecked = MutableLiveData<Int>(-1)
}