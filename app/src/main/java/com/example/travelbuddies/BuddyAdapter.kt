package com.example.travelbuddies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BuddyAdapter(val requiredContext: Context,private val  buddieslist: ArrayList<buddy>) : RecyclerView.Adapter<BuddyAdapter.buddyViewHolder>() {

    class buddyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.textView2)
        val email = itemView.findViewById<TextView>(R.id.textView3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): buddyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return buddyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return buddieslist.size
    }

    override fun onBindViewHolder(holder: buddyViewHolder, position: Int) {
        val buddy = buddieslist[position]
        holder.name.text = buddy.name
        holder.email.text = buddy.email
    }


}
