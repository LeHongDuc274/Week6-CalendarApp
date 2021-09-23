package com.example.calendarapp.adapter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calendarapp.MonthFragment
import com.example.calendarapp.R

class FragmentCollectionAdapter(
    activity: FragmentActivity,
) : FragmentStateAdapter(activity) {
    var fragment = MonthFragment()
    val firstElementPosition = Int.MAX_VALUE / 2

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        fragment = MonthFragment()
        fragment.index = position
        return fragment
    }
}