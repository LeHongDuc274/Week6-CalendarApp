package com.example.calendarapp.adapter

import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.MyCalendar
import com.example.calendarapp.R
import com.example.calendarapp.models.DayInMonth
import java.util.*

class CalendarAdapter(val date: Date, val llDayOfMonth: ConstraintLayout) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var listener2: ((Int, MyCalendar, Int) -> Unit)? = null
    var endCurMonth = 0

    var startCurMonth = 0
    var dateList = arrayListOf<DayInMonth>()
    var myCalendar = MyCalendar(date)
    var checkedPosition = listOf<Int>()
    var colorChecked = Color.GREEN
    var colorUnChecked = Color.WHITE
    fun setDayStart(start: Int) {
        myCalendar.startDay = start
        myCalendar.initCalendar()
        dateList = myCalendar.dateList
        startCurMonth = myCalendar.dayOfPrevMonth
        endCurMonth = dateList.size - myCalendar.dayOfNextMonth - 1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvDay = holder.itemView.findViewById<TextView>(R.id.tv_item_day)
        val height = llDayOfMonth.height / 6

        val mPos = holder.adapterPosition
        //setText
        tvDay.text = dateList[position].value.toString()
        tvDay.layoutParams.height = height

        if (mPos < startCurMonth || mPos > endCurMonth) {
            tvDay.setTextAppearance(R.style.TextOtherMonth)
        } else tvDay.setTextAppearance(R.style.TextCurMonth)

        if (checkedPosition.contains(mPos)) {
            holder.itemView.setBackgroundColor(colorChecked)
        } else {
            holder.itemView.setBackgroundColor(colorUnChecked)
        }

        var i = 0
        if (mPos >= startCurMonth && mPos <= endCurMonth) {
            holder.itemView.setOnClickListener {
                i++
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    if (i == 1) {
                        Unit
                    } else if (i == 2) {
                        listener2?.invoke(mPos, myCalendar, startCurMonth)
                    }
                    i = 0
                }, 300L)
            }
        } else  holder.itemView.isClickable = false
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    fun setCheckPostion(list: List<Int>) {
        checkedPosition = list
        this.notifyDataSetChanged()
    }

    fun setClick2(action: (Int, MyCalendar, Int) -> Unit) {
        listener2 = action
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}