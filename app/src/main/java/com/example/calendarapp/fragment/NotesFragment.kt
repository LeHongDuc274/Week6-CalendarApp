package com.example.calendarapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.adapter.NoteAdapter
import com.example.calendarapp.viewmodels.NoteViewModel


class NotesFragment : Fragment() {
    lateinit var rv_notes: RecyclerView
    lateinit var noteViewModel : NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        rv_notes = view.findViewById(R.id.rv_notes)
        noteViewModel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
        val adapter = NoteAdapter()
        rv_notes.setHasFixedSize(true)
        rv_notes.layoutManager = LinearLayoutManager(activity)
        rv_notes.adapter = adapter
        noteViewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            adapter.listNotes = it
        })
        val divider = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)
        rv_notes.addItemDecoration(divider)
        return view
    }
}