package com.example.calendarapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
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
import com.example.calendarapp.models.Note
import com.example.calendarapp.viewmodels.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesFragment : Fragment() {
    lateinit var rv_notes: RecyclerView
    lateinit var fab : FloatingActionButton
    lateinit var back : FloatingActionButton
    lateinit var noteViewModel: NoteViewModel
    lateinit var adapter: NoteAdapter
    var list  = listOf<Note>()
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
            list = it.toList()
        })
        val divider = DividerItemDecoration(activity, GridLayoutManager.VERTICAL)
        rv_notes.addItemDecoration(divider)
    }

    private fun initViews(view: View) {
        rv_notes = view.findViewById(R.id.rv_notes)
        fab = view.findViewById(R.id.fab)
        back = view.findViewById(R.id.back)
        back.hide()
        noteViewModel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
        adapter = NoteAdapter()
        fab.setOnClickListener {
            showSearchDialog()
        }
        back.setOnClickListener {
            fab.show()
            back.hide()
            adapter.setListNote(list)
        }
    }

    private fun showSearchDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.note_edit, null)
        val edt = dialogLayout.findViewById<EditText>(R.id.edt_note)
        with(builder) {
            setTitle("Search Content")
            setPositiveButton("Search") { dialog, which ->
                fab.hide()
                back.show()
                val keyword = edt.text.toString()
                search(keyword)
            }
            setNegativeButton("Cancel") { dialog, which -> Unit }
            setView(dialogLayout)
            show()
        }
    }

    private fun search(keyword: String) {
        val listTemp = list.filter { note->
            note.content.contains(keyword,ignoreCase = false)
        }
       // Toast.makeText(activity,list.sizeTemp.toString(),Toast.LENGTH_SHORT).show()
        adapter.setListNote(listTemp)
    }
}