package com.example.calendarapp.others

import android.os.Handler
import android.os.Looper
import android.view.View

open class DoubleClick(
    private val doubleClickListenẻr: DoubleClickListener,
    private val timeDelay: Long = 200L
) : View.OnClickListener {

    private val handler = Handler(Looper.getMainLooper())
    private var countCLicks = 0
    private var isBusy = false

    override fun onClick(v: View?) {
        if (!isBusy) {
            isBusy = true
            countCLicks++
            handler.postDelayed({
                if (countCLicks >= 2) doubleClickListenẻr.onDoubleClickEvent(v)
                if (countCLicks == 1) doubleClickListenẻr.onSingleClickEvent(v)
            }, timeDelay)
            isBusy = false
        }

    }
}