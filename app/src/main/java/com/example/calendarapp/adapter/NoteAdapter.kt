package com.example.calendarapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.models.Note

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var listNotes = listOf<Note>()
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_content_note).text = listNotes[position].content
        holder.itemView.findViewById<TextView>(R.id.tv_day_note).text  = listNotes[position].date.toString()
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
    fun setListNote(list:List<Note>){
        listNotes = list
        notifyDataSetChanged()
    }
}