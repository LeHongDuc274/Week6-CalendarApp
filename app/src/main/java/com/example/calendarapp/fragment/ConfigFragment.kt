package com.example.calendarapp.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.calendarapp.R
import com.example.calendarapp.models.Note

class ConfigFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_config, container, false)
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

    }

    private fun saveRefs(value: String) {
        if (value.length >= 6){
            val sharedPref = activity?.getSharedPreferences("Pass", Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.key), value)
                apply()
            }
            Toast.makeText(activity,"Save succes",Toast.LENGTH_SHORT).show()
        } else Toast.makeText(activity,"PassWord  < 6 Charator,Fail",Toast.LENGTH_SHORT).show()
    }
}