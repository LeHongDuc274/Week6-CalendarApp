package com.example.calendarapp.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.calendarapp.R
import com.example.calendarapp.database.NoteDatabase
import com.example.calendarapp.models.Note
import com.example.calendarapp.others.CSVReader
import com.example.calendarapp.others.CSVWriter
import com.example.calendarapp.viewmodels.NoteViewModel
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class ConfigFragment : Fragment() {
    lateinit var noteViewModel: NoteViewModel
    var newNotes: List<Note> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_config, container, false)
        noteViewModel = ViewModelProvider(
            requireActivity(), NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
        initControls(view)
        return view
    }

    private fun initControls(view: View) {
        view.findViewById<TextView>(R.id.tv_pass_word).setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.note_edit, null)
            val edt = dialogLayout.findViewById<EditText>(R.id.edt_note)
            with(builder) {
                setTitle("Set PassWord")
                setPositiveButton("Save") { dialog, which ->
                    val value = edt.text.toString()
                    saveRefs(value)
                }
                setNegativeButton("Cancel") { dialog, which -> Unit }
                setView(dialogLayout)
                show()
            }
        }
        view.findViewById<TextView>(R.id.tv_backup)
            .setOnClickListener { if (isExternalStorageWritable()) backup() }
        view.findViewById<TextView>(R.id.tv_restore)
            .setOnClickListener { if (isExternalStorageReadable()) restore() }
    }

    private fun saveRefs(value: String) {
        if (value.length >= 6) {
            val sharedPref = activity?.getSharedPreferences("Pass", Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.key), value)
                apply()
            }
            Toast.makeText(activity, "Save succes", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(activity, "PassWord  < 6 Charator,Fail", Toast.LENGTH_SHORT).show()
    }

    private fun backup() {
        val db = NoteDatabase.getInstance(requireContext())
        val exportDir = File(requireContext().getExternalFilesDir(null), "/BackUp")
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
        val file = File(exportDir, "notes.csv")
        try {
            file.createNewFile()
            val csvWriter = CSVWriter(FileWriter(file))
            val curCSV = db.query("select * from note_table", null)
            //export title va Date , khong export ID
            val arrStrColumName = arrayOfNulls<String>(curCSV.columnCount - 1)
            for (i in 0 until curCSV.columnCount - 1) {
                arrStrColumName[i] = curCSV.columnNames[i]
            }
            csvWriter.writerNext(arrStrColumName)
            while (curCSV.moveToNext()) {
                val arrStr = arrayOfNulls<String>(curCSV.columnCount - 1)
                for (i in 0 until curCSV.columnCount - 1) {
                    arrStr[i] = curCSV.getString(i)
                }
                csvWriter.writerNext(arrStr)
            }
            csvWriter.close()
            curCSV.close()
            Toast.makeText(activity, "Exported SuccessFully", Toast.LENGTH_SHORT).show()
        } catch (sqlEx: Exception) {
            Log.e("sqlEx", sqlEx.toString())
        }
    }

    private fun restore() {
        noteViewModel.deleteAll()
        val item = mutableListOf<Pair<String, Long>>()
        //Log.e( "tt", requireContext().getExternalFilesDir(null).toString())
        val csvReader =
            CSVReader(FileReader("${requireContext().getExternalFilesDir(null)}/BackUp/notes.csv"))
        var nextLine: Array<String>? = null
        var count = 0
        do {
            nextLine = csvReader.readNext()
            nextLine?.let { nextline ->
                if (count == 0) {                             // count==0 ->Unit ->nẽtLine
                } else if (count == 1) {
                    item.add(Pair(nextline[0], nextline[1].toLong()))
                }
                count = 1
            }
        } while ((nextLine) != null)
        csvReader.close()

        newNotes = item.map {
            Note(it.first, fromTimestamp(it.second))
        }
        newNotes.forEach {
            noteViewModel.insertNote(it)
        }
        Toast.makeText(activity, "Imported SuccessFully", Toast.LENGTH_SHORT).show()
    }

    private fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

}