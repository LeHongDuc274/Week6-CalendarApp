package com.example.calendarapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.calendarapp.R
import com.example.calendarapp.adapter.FragmentCollectionAdapter


class CalendarFragment : Fragment() {
    lateinit var fragmentCollectionAdapter : FragmentCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager)
        initViews()
    }
    fun initViews() {
        fragmentCollectionAdapter = FragmentCollectionAdapter(requireActivity())
        viewPager.adapter = fragmentCollectionAdapter
        fragmentCollectionAdapter.apply {
            viewPager.setCurrentItem(this.firstElementPosition, false)
        }
    }
}