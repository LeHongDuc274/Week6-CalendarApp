package com.example.calendarapp.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.MyCalendar
import com.example.calendarapp.R
import com.example.calendarapp.models.DayInMonth
import com.example.calendarapp.others.DoubleClick
import com.example.calendarapp.others.DoubleClickListener
import com.example.calendarapp.viewmodels.MyViewModel
import java.util.*

class CalendarAdapter(val date: Date, val llDayOfMonth: ConstraintLayout) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var viewModel: MyViewModel

    // var listener : ((Int) -> Unit)? = null
    var endCurMonth = 0
    var curMonth = -1
    var checkedMonth = -1
    var startCurMonth = 0
    var dateList = arrayListOf<DayInMonth>()
    var myCalendar = MyCalendar(date)
    var checkedPosition = -1
    var colorChecked = Color.GREEN
    var colorUnChecked = Color.WHITE
    fun setDayStart(start: Int) {
        myCalendar.startDay = start
        myCalendar.initCalendar()
        curMonth = myCalendar.month
        dateList = myCalendar.dateList
        startCurMonth = myCalendar.dayOfPrevMonth
        endCurMonth = dateList.size - myCalendar.dayOfNextMonth - 1
        // Log.e("tagg",startCurMonth.toString() + "  " + endCurMonth.toString())
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

        var mPos = holder.adapterPosition
        //setText
        tvDay.text = dateList[position].value.toString()
        tvDay.layoutParams.height = height

        if (mPos < startCurMonth || mPos > endCurMonth) {
            tvDay.setTextAppearance(R.style.TextOtherMonth)
        } else tvDay.setTextAppearance(R.style.TextCurMonth)
        if (checkedPosition == mPos ) {
            holder.itemView.setBackgroundColor(colorChecked)
        } else {
            holder.itemView.setBackgroundColor(colorUnChecked)
        }

        //double click
        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
                notifyItemChanged(checkedPosition)
                if (checkedPosition != mPos) {
                    checkedPosition = mPos

                    colorChecked = Color.GREEN
                    notifyDataSetChanged()
                   // Log.e ("SingleClick", checkedMonth.toString()+curMonth.toString())
                }
            }

            override fun onDoubleClickEvent(view: View?) {
                notifyItemChanged(checkedPosition)
                if (checkedPosition != mPos) {
                    checkedPosition = mPos
                    colorChecked = Color.RED
                    notifyItemChanged(mPos)
                    //  Log.e("doubleClick", "y")
                }
            }
        })
        holder.itemView.setOnClickListener(doubleClick)

    }


    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}