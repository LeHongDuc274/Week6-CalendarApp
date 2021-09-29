package com.example.calendarapp.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ConfigFragment : Fragment() {
    lateinit var noteViewModel: NoteViewModel
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
        view.findViewById<TextView>(R.id.tv_backup).setOnClickListener {
            backup()
        }
        view.findViewById<TextView>(R.id.tv_restore).setOnClickListener {
            restore()
        }
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
            val arrStrColumName = arrayOfNulls<String>(curCSV.columnCount-1)
            for (i in 0 until curCSV.columnCount - 1) {
                arrStrColumName[i] = curCSV.columnNames[i]
            }
            csvWriter.writerNext(arrStrColumName)
            while (curCSV.moveToNext()) {
                //Which column you want to export
                val arrStr = arrayOfNulls<String>(curCSV.columnCount-1)
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
        // Mở thư viện -> chọn file -> Import to database

//        val csvReader =
//            CSVReader(FileReader("${requireContext().getExternalFilesDir(null)}/BackUp/notes.csv"))/* path of local storage (it should be your csv file locatioin)*/
//        var nextLine: Array<String> ? = null
//        var count = 0
//        val columns = StringBuilder()
//        GlobalScope.launch(Dispatchers.IO) {
//            do {
//                val value = StringBuilder()
//                nextLine = csvReader.readNext()
//                nextLine?.let {nextLine->
//                    for (i in 0 until nextLine.size - 1) {
//                        if (count == 0) {                             // the count==0 part only read
//                            if (i == nextLine.size - 2) {             //your csv file column name
//                                columns.append(nextLine[i])
//                                count =1
//                            }
//                            else
//                                columns.append(nextLine[i]).append(",")
//                        } else {                         // this part is for reading value of each row
//                            if (i == nextLine.size - 2) {
//                                value.append("'").append(nextLine[i]).append("'")
//                                count = 2
//                            }
//                            else
//                                value.append("'").append(nextLine[i]).append("',")
//                        }
//                    }
//                    if (count==2) {
//                        noteViewModel.pushCustomerData(columns, value)//write here your code to insert all values
//                    }
//                }
//            }while ((nextLine)!=null)
//        }
//
//        Toast.makeText(activity,"Imported SuccessFully",Toast.LENGTH_SHORT).show()
   }

}