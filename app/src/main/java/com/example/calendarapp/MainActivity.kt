package com.example.calendarapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.calendarapp.adapter.FragmentCollectionAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentCollectionAdapter
    private lateinit var localDate: LocalDate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        initViews()
    }
    fun initViews(){

        val fragmentCollectionAdapter = FragmentCollectionAdapter(this)
        viewPager.adapter = fragmentCollectionAdapter
       fragmentCollectionAdapter.apply {
           viewPager.setCurrentItem(this.firstElementPosition,false)
       }
    }
}