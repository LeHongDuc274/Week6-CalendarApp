package com.example.calendarapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.adapter.CalendarAdapter
import com.example.calendarapp.adapter.DayWeekAdapter
import com.example.calendarapp.models.Note
import com.example.calendarapp.viewmodels.MyViewModel
import com.example.calendarapp.viewmodels.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class MonthFragment() : Fragment() {
    var index: Int = 0
    var listDayWeek = arrayListOf<String>()
    lateinit var monthOfIndex: Calendar
    var listPosition = listOf<Int>()
    var listPositionAfter = listOf<Int>()
    lateinit var rvDayOfWeek: RecyclerView
    lateinit var dayWeekAdapter: DayWeekAdapter
    lateinit var rvDayofMonth: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter
    lateinit var viewmodel: MyViewModel
    lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        viewmodel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        noteViewModel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]

        initViewDayOfWeek(view)
        initViewCalendar(view)

        observeStartDay()
        observeCheckedDay()

        return view
    }

    private fun observeCheckedDay() {
        calendarAdapter.setClick2 { pos, myCalendar, startDay ->
            val time = myCalendar.calendar
            time.set(Calendar.DATE, pos - startDay + 1)
            val textTime = SimpleDateFormat("EEE, d MMM yyyy").format(time.time)
            val builder = AlertDialog.Builder(activity)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.note_edit, null)
            val edt = dialogLayout.findViewById<EditText>(R.id.edt_note)
            with(builder) {
                setTitle(textTime)
                setPositiveButton("Ok") { dialog, which ->
                    // Toast.makeText(activity,time.time.toString(),Toast.LENGTH_SHORT).show()
                    val note = Note(edt.text.toString(), time.time)
                    noteViewModel.insertNote(note)
                }
                setNegativeButton("cancel") { dialog, which ->
                    //Log.e("cancle","duc")
                }
                setView(dialogLayout)
                show()
            }

        }

        noteViewModel.getAllNote().observe(viewLifecycleOwner, {
            val listNoteOnMonth = it.filter { note ->
                fillterList(note)
            }
            listPosition = listNoteOnMonth.map {
                mapList(it) + calendarAdapter.startCurMonth - 1
            }
            //listPositionAfter = listPosition
            calendarAdapter.setCheckPostion(listPosition)
            //Log.e("tag2",listPosition.size.toString() + monthOfIndex.get(Calendar.MONTH) )
        })
        viewmodel._startDay.observe(viewLifecycleOwner) { it1 ->

            val list = updateListChecked(listPosition,it1)
            //  Log.e("tag3", listPosition.size.toString())
            calendarAdapter.setCheckPostion(list)
        }
    }

    private fun updateListChecked(listPosition: List<Int>,it1:Int): List<Int> {
        val list : List<Int>
        val listTemp = listPosition.toList()
        list = listTemp.map {
            it1 - it
        }
        return list
    }

    private fun fillterList(note: Note): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = note.date
        return (calendar.get(Calendar.MONTH) == monthOfIndex.get(Calendar.MONTH) &&
                calendar.get(Calendar.YEAR) == monthOfIndex.get(Calendar.YEAR))
    }

    private fun mapList(note: Note): Int {
        val calendar = Calendar.getInstance()
        calendar.time = note.date
        return calendar.get(Calendar.DATE)
    }

    private fun observeStartDay() {
        viewmodel._startDay.observe(viewLifecycleOwner) { it1 ->
            updateList(it1)
            calendarAdapter.setDayStart(it1)

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
        monthOfIndex = Calendar.getInstance().apply {
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
