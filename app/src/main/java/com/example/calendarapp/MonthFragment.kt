package com.example.calendarapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.calendarapp.adapter.CalendarAdapter
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class MonthFragment() : Fragment() {
    var index: Int = 0
    lateinit var rvDayofMonth: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        initView(view)
        return view
    }


    @SuppressLint("SimpleDateFormat", "UseCompatLoadingForDrawables")
    private fun initView(view: View) {
        index = index - (Int.MAX_VALUE / 2)
        val monthOfIndex = Calendar.getInstance().apply {
            add(Calendar.MONTH, index)
            time
        }
        val llDayOfMonth = view.findViewById<ConstraintLayout>(R.id.ll_day_of_month)

        val textMonth = SimpleDateFormat("MMMM yyyy").format(monthOfIndex.time)
        view.findViewById<TextView>(R.id.tv_month).text = textMonth.toString()

        calendarAdapter = CalendarAdapter(monthOfIndex.time, llDayOfMonth)
        rvDayofMonth = view.findViewById<RecyclerView>(R.id.rv_day_of_month)

        val divider1 = DividerItemDecoration(activity, GridLayoutManager.HORIZONTAL)
        val divider2 = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)

        rvDayofMonth.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
            addItemDecoration(divider2)
            addItemDecoration(divider1)
        }
    }
}
