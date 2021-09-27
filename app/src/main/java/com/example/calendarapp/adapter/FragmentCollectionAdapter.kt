package com.example.calendarapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calendarapp.fragment.MonthFragment

class FragmentCollectionAdapter(
    activity: FragmentActivity
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