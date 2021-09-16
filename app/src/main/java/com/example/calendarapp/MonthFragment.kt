package com.example.calendarapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.adapter.CalendarAdapter
import com.example.calendarapp.adapter.DayWeekAdapter
import java.text.SimpleDateFormat
import java.util.*


class MonthFragment() : Fragment() {
    var index: Int = 0
    var startDay = 1
    var listDayWeek = arrayListOf<String>()
    lateinit var rvDayOfWeek : RecyclerView
    lateinit var dayWeekAdapter: DayWeekAdapter
    lateinit var rvDayofMonth: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        initViewDayOfWeek(view)
        initViewCalendar(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.tool_bar_main)
        setupMenu(toolBar)
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
        calendarAdapter.setDayStart(startDay)
        rvDayofMonth = view.findViewById<RecyclerView>(R.id.rv_day_of_month)

        val divider1 = DividerItemDecoration(activity, GridLayoutManager.HORIZONTAL)
        val divider2 = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)

        rvDayofMonth.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
            addItemDecoration(divider2)
        }
    }
    private fun setupMenu(toolBar: Toolbar?) {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.su -> {
                    startDay = 1
                    calendarAdapter.setDayStart(1)
                    updateList()
                }
                R.id.mo ->{
                    startDay = 2
                    calendarAdapter.setDayStart(2)
                    updateList()
                }
                R.id.tu -> {
                    startDay = 3
                    calendarAdapter.setDayStart(3)
                    updateList()
                }
                R.id.we -> {
                    startDay = 4
                    calendarAdapter.setDayStart(4)
                    updateList()
                }
                R.id.th -> {
                    startDay = 5
                    calendarAdapter.setDayStart(0)
                    updateList()
                }
                R.id.fr -> {
                    startDay = 6
                    calendarAdapter.setDayStart(-1)
                    updateList()
                }
                R.id.sa -> {
                    startDay = 7
                    calendarAdapter.setDayStart(-2)
                    updateList()
                }
            }
            true
        }
    }


    fun updateList(){
        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        for (i in 0 until (startDay-1)){
            list1.add(listDayWeek[i])
        }
        for (i in (startDay-1)..6){
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
