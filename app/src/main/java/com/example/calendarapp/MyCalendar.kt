package com.example.calendarapp

import android.util.Log
import com.example.calendarapp.models.DayInMonth
import java.util.*

class MyCalendar(val date: Date) {
    companion object {
        const val DAYS_OF_WEEK = 7
        const val MAX_WEEK_OF_MONTH = 6
    }
    var month = 0
    var year = 0
    var startDay = 1
    val calendar = Calendar.getInstance()
    var dayOfPrevMonth = 0
    var dayOfNextMonth = 0
    var dayOfCurrentMonth = 0
    var dateList = arrayListOf<DayInMonth>()

    init {
        calendar.time = date
    }

    fun initCalendar() {

        dateList.clear()
        calendar.set(Calendar.DATE, 1)
        month = calendar[Calendar.MONTH]
        year = calendar[Calendar.YEAR]
        dayOfCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        if(calendar.get(Calendar.DAY_OF_WEEK) >= startDay) {
            dayOfPrevMonth = calendar.get(Calendar.DAY_OF_WEEK) - startDay // 1->7//sunday->satuday
        }   else  dayOfPrevMonth = calendar.get(Calendar.DAY_OF_WEEK) - startDay + 7
      //  Log.e("dayOfPrevMonth", dayOfPrevMonth.toString())
        getDayOfPrevMonth(calendar.clone() as Calendar)
        getDayOfCurrentMonth(calendar)
        dayOfNextMonth = MAX_WEEK_OF_MONTH * DAYS_OF_WEEK - (dayOfPrevMonth + dayOfCurrentMonth)
        getDayOfNextMonth()
    }

    private fun getDayOfPrevMonth(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH - 1))
        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var maxOffsetDate = maxDayOfMonth - dayOfPrevMonth
        for (i in 1..dayOfPrevMonth) dateList.add(DayInMonth(++maxOffsetDate))
    }

    private fun getDayOfCurrentMonth(calendar: Calendar) {
        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..maxDayOfMonth) dateList.add(DayInMonth(i))
    }

    private fun getDayOfNextMonth() {
        for (i in 1..dayOfNextMonth) dateList.add(DayInMonth(i))
    }
}