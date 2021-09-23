package com.example.calendarapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.adapter.CalendarAdapter
import com.example.calendarapp.adapter.DayWeekAdapter
import com.example.calendarapp.viewmodels.MyViewModel
import java.text.SimpleDateFormat
import java.util.*


class MonthFragment() : Fragment() {
    var index: Int = 0
    var listDayWeek = arrayListOf<String>()
    lateinit var rvDayOfWeek: RecyclerView
    lateinit var dayWeekAdapter: DayWeekAdapter
    lateinit var rvDayofMonth: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter
    lateinit var viewmodel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        viewmodel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        initViewDayOfWeek(view)
        initViewCalendar(view)

        observeStartDay()
        observeCheckedDay()

        return view
    }

    private fun observeCheckedDay() {
        calendarAdapter.setClick { pos, month, year ->
            viewmodel._otherDayChecked.value = pos
            viewmodel._otherMonthChecked.value = month
            viewmodel._otherYearChecked.value = year
        }
        viewmodel._otherMonthChecked.observe(viewLifecycleOwner) {
            if (it != calendarAdapter.curMonth || calendarAdapter.curYear != viewmodel._otherYearChecked.value) {
                calendarAdapter.checkedPosition = -1
                calendarAdapter.notifyDataSetChanged()
            } else {
                calendarAdapter.checkedPosition = viewmodel._otherDayChecked.value!!
                calendarAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeStartDay() {
        viewmodel._startDay.observe(viewLifecycleOwner) {
            updateList(it)
            calendarAdapter.setDayStart(it)
            calendarAdapter.notifyDataSetChanged()
        }
    }


    private fun initViewDayOfWeek(view: View) {
        createListDayOfWeek()
        rvDayOfWeek = view.findViewById(R.id.rv_day_of_week)
        dayWeekAdapter = DayWeekAdapter()
        dayWeekAdapter.setListDayOfWeeks(listDayWeek)
        rvDayOfWeek.adapter = dayWeekAdapter
        rvDayOfWeek.layoutManager = GridLayoutManager(requireContext(), 7)
    }

    @SuppressLint("SimpleDateFormat", "UseCompatLoadingForDrawables")
    private fun initViewCalendar(view: View) {
        //index = 0
        index = index - (Int.MAX_VALUE / 2)
        val monthOfIndex = Calendar.getInstance().apply {
            add(Calendar.MONTH, index)
            time
        }

        val llDayOfMonth = view.findViewById<ConstraintLayout>(R.id.ll_day_of_month)
        val textMonth = SimpleDateFormat("MMMM yyyy").format(monthOfIndex.time)
        view.findViewById<TextView>(R.id.tv_month).text = textMonth.toString()

        calendarAdapter = CalendarAdapter(monthOfIndex.time, llDayOfMonth)
        viewmodel._startDay.value?.let {
            calendarAdapter.setDayStart(it)
        }

        rvDayofMonth = view.findViewById<RecyclerView>(R.id.rv_day_of_month)
        val divider2 = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)
        rvDayofMonth.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
            addItemDecoration(divider2)
        }
    }


    fun updateList(startDay: Int = 1) {
        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        for (i in 0 until (startDay - 1)) {
            list1.add(listDayWeek[i])
        }
        for (i in (startDay - 1)..6) {
            list2.add(listDayWeek[i])
        }
        val listDayWeekClone = arrayListOf<String>()
        listDayWeekClone.addAll(list2)
        listDayWeekClone.addAll(list1)
        dayWeekAdapter.setListDayOfWeeks(listDayWeekClone)
    }

    private fun createListDayOfWeek() {
        listDayWeek.apply {
            add("Su")
            add("Mo")
            add("Tu")
            add("We")
            add("Thu")
            add("Fr")
            add("Sa")
        }
    }


}
