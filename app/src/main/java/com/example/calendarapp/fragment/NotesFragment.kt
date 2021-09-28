package com.example.calendarapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.adapter.NoteAdapter
import com.example.calendarapp.viewmodels.NoteViewModel


class NotesFragment : Fragment() {
    lateinit var rv_notes: RecyclerView
    lateinit var noteViewModel: NoteViewModel
    lateinit var adapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        initViews(view)
        setUpRecyclerView()
        return view
    }

    private fun setUpRecyclerView() {
        adapter.clickItem { note ->
            val args = bundleOf(
                "note" to note
            )
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            with(transaction) {
                replace(R.id.fragment_container_view, DetailFragment::class.java,args)
                addToBackStack(null)
                commit()
            }
        }
        rv_notes.setHasFixedSize(true)
        rv_notes.layoutManager = LinearLayoutManager(activity)
        rv_notes.adapter = adapter
        noteViewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            adapter.setListNote(it)
        })
        val divider = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)
        rv_notes.addItemDecoration(divider)
    }

    private fun initViews(view: View) {
        rv_notes = view.findViewById(R.id.rv_notes)
        noteViewModel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
        adapter = NoteAdapter()
    }
}