package com.example.calendarapp.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.MyCalendar
import com.example.calendarapp.R
import com.example.calendarapp.models.DayInMonth
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(val date: Date, val llDayOfMonth: ConstraintLayout) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    lateinit var context : Context
    var dateList = arrayListOf<DayInMonth>()
    var myCalendar = MyCalendar(date)
    var checkedPosition = -1

    init {
        myCalendar.initCalendar()
        dateList = myCalendar.dateList
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

        //holder.itemView.layoutParams.height = height
        tvDay.text = dateList[position].value.toString()
        tvDay.layoutParams.height = height
        if (checkedPosition == position) {
            tvDay.setTextColor(Color.parseColor(context.getString(R.string.color_tv_checked)))
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.color_background_item_checked)))
        } else {
            tvDay.setTextColor(Color.parseColor(context.getString(R.string.color_tv_normal)))
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.color_background_item_normal)))
        }

        if (position < startCurMonth || position > endCurMonth) {
            tvDay.setTextAppearance(R.style.TextOtherMonth)
        }
        holder.itemView.setOnClickListener {
            if (checkedPosition != position) {
                checkedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}