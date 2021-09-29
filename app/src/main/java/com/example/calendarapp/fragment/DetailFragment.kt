package com.example.calendarapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.calendarapp.R
import com.example.calendarapp.models.Note
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val args = arguments?.get("note") as Note
        setUpView(args, view)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpView(args: Note?, view: View) {
        if (args != null) {
        view.findViewById<TextView>(R.id.tv_content).text = args.content

            view.findViewById<TextView>(R.id.tv_time).text = SimpleDateFormat("EEE, d MMM yyyy").format(args.date)
        }
    }

}