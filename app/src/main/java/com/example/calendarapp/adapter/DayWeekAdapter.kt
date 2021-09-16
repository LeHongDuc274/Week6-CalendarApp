package com.example.calendarapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R

class DayWeekAdapter : RecyclerView.Adapter<DayWeekAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

   private var listDayOfWeek: ArrayList<String> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.tv_item_day).text = listDayOfWeek[position]
    }

    override fun getItemCount(): Int {
        return listDayOfWeek.size
    }

    fun setListDayOfWeeks(list : ArrayList<String>){
        this.listDayOfWeek = list
        notifyDataSetChanged()
    }
}
