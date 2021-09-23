package com.example.calendarapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.calendarapp.adapter.FragmentCollectionAdapter
import com.example.calendarapp.viewmodels.MyViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var viewmodel : MyViewModel

    lateinit var fragmentCollectionAdapter : FragmentCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProvider(this)[MyViewModel::class.java]
        viewPager = findViewById(R.id.viewPager)
        toolbar = findViewById(R.id.tool_bar_main)
        setupMenu(toolbar)
        initViews()
    }

    fun initViews() {
        fragmentCollectionAdapter = FragmentCollectionAdapter(this)
        viewPager.adapter = fragmentCollectionAdapter
        fragmentCollectionAdapter.apply {
            viewPager.setCurrentItem(this.firstElementPosition, false)
        }
    }
    private fun setupMenu(toolBar: androidx.appcompat.widget.Toolbar?) {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.su -> {
                    viewmodel._startDay.value= 1

                }
                R.id.mo ->{
                    viewmodel._startDay.value= 2

                }
                R.id.tu -> {
                    viewmodel._startDay.value= 3
                    Log.e("viewmodel",viewmodel._startDay.value.toString())
                }
                R.id.we -> {
                    viewmodel._startDay.value= 4

                }
                R.id.th -> {
                    viewmodel._startDay.value= 5
                }
                R.id.fr -> {
                    viewmodel._startDay.value= 6

                }
                R.id.sa -> {
                    viewmodel._startDay.value= 7

                }
            }
            true
        }
    }
}