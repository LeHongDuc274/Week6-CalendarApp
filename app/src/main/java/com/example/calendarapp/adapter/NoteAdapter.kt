package com.example.calendarapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.models.Note
import java.text.SimpleDateFormat

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var listNotes = listOf<Note>()
    private var click : ((Note) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_content_note).text =
            listNotes[position].content
        if (listNotes[position].date != null) {
            holder.itemView.findViewById<TextView>(R.id.tv_day_note).text = position.toString() + ") " +
                SimpleDateFormat("EEE, d MMM yyyy").format(listNotes[position].date)
        }
        holder.itemView.setOnClickListener {
            click?.invoke(listNotes[position])
        }
    }
    override fun getItemCount(): Int {
        return listNotes.size
    }

    fun setListNote(list: List<Note>) {
        listNotes = list
        notifyDataSetChanged()
    }
    fun clickItem(action:(Note)->Unit){
        click= action
    }
}