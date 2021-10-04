package com.example.calendarapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.R
import com.example.calendarapp.models.Note
import com.example.calendarapp.viewmodels.NoteViewModel
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {
    lateinit var viewmodel: NoteViewModel
    lateinit var edt: EditText
    lateinit var tv: TextView
    lateinit var btnSave: Button
    lateinit var btnDelete: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        viewmodel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
        initView(view)
        val args = arguments?.get("note") as Note
        setUpView(args, view)
        return view
    }

    private fun initView(view: View) {
        edt = view.findViewById<EditText>(R.id.edt_content)
        tv = view.findViewById<TextView>(R.id.tv_time)
        btnSave = view.findViewById<Button>(R.id.btn_save)
        btnDelete = view.findViewById(R.id.btn_delete)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpView(args: Note?, view: View) {
        btnSave.setOnClickListener {
            updateContentNote(args)
        }
        btnDelete.setOnClickListener {
            deleteNote(args)
        }

        args?.let {
            tv.setText(SimpleDateFormat("EEE, d MMM yyyy").format(it.date))
            edt.setText(it.content)
        }
    }

    private fun deleteNote(args: Note?) {
        args?.let {
            viewmodel.deleteNote(it)
            activity?.supportFragmentManager?.popBackStack()
            Toast.makeText(activity, "Delete SuccesFully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateContentNote(args: Note?) {
        val newContent = edt.text.toString()
        args?.let {
            it.content = newContent
            viewmodel.updateNote(it)
            Toast.makeText(activity, "Update SuccesFully", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}