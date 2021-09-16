package com.example.calendarapp.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.MyCalendar
import com.example.calendarapp.R
import com.example.calendarapp.models.DayInMonth
import com.example.calendarapp.others.DoubleClick
import com.example.calendarapp.others.DoubleClickListener
import java.util.*

class CalendarAdapter(val date: Date, val llDayOfMonth: ConstraintLayout) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    lateinit var context: Context
    var dateList = arrayListOf<DayInMonth>()
    var myCalendar = MyCalendar(date)
    var checkedPosition = -1

    fun setDayStart(start: Int) {
        myCalendar.startDay = start
        myCalendar.initCalendar()
        dateList = myCalendar.dateList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context = holder.itemView.context
        val tvDay = holder.itemView.findViewById<TextView>(R.id.tv_item_day)
        val height = llDayOfMonth.height / 6
        val startCurMonth = myCalendar.dayOfPrevMonth
        val endCurMonth = dateList.size - myCalendar.dayOfNextMonth - 1
        var mPos = holder.adapterPosition
        //setText
        tvDay.text = dateList[position].value.toString()
        tvDay.layoutParams.height = height
        //setColor next-prevMonth
        if (checkedPosition == mPos) {
            tvDay.setTextColor(Color.parseColor(context.getString(R.string.color_tv_checked)))
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.color_background_item_checked)))
        } else {
            tvDay.setTextColor(Color.parseColor(context.getString(R.string.color_tv_normal)))
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.color_background_item_normal)))
        }
        if (mPos < startCurMonth || mPos > endCurMonth) {
            tvDay.setTextAppearance(R.style.TextOtherMonth)
        }
        //double click
        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
                if (checkedPosition != mPos) {
                    checkedPosition = mPos
                    notifyDataSetChanged()
                    Log.e("SingleClick", "y")
                }
            }

            override fun onDoubleClickEvent(view: View?) {
                if (checkedPosition != mPos) {
                    checkedPosition = mPos
                    notifyDataSetChanged()
                    Log.e("doubleClick", "y")
                }
            }
        })

        holder.itemView.setOnClickListener(doubleClick)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}