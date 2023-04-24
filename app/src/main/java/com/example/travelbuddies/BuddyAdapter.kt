package com.example.travelbuddies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase

class BuddyAdapter(val requiredContext: Context,var buddieslist: ArrayList<buddy>,var uid:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var friend=1
    var notfriend=0
    var database= Firebase.database.reference
    private var onCLickListener: OnClickListener?=null

    inner class buddyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.buddyname)
        val gender = itemView.findViewById<TextView>(R.id.buddygender)
        val pic = itemView.findViewById<ImageView>(R.id.buddypic)


    }
    inner class friendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById<TextView>(R.id.buddyname)
        val gender = itemView.findViewById<TextView>(R.id.buddygender)
        val pic = itemView.findViewById<ImageView>(R.id.buddypic)
        val email=itemView.findViewById<TextView>(R.id.buddyemail)
        val phone=itemView.findViewById<TextView>(R.id.buddyphone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==friend){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.friends, parent, false)
            return friendViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return buddyViewHolder(view)

    }

    override fun getItemViewType(position: Int): Int {
        val buddy=buddieslist[position]


        if(buddy.friend){
            return friend
        }
        return notfriend

    }

    override fun getItemCount(): Int {
        return buddieslist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val buddy = buddieslist[position]
        if(holder.itemViewType==friend){
            val viewholder=holder as friendViewHolder
            viewholder.name.text = buddy.name
            viewholder.gender.text = buddy.gender
            viewholder.email.text=buddy.email
            viewholder.phone.text=buddy.phone


//       pic ka khel
            Glide.with(viewholder.pic.context).load(buddy.photo).circleCrop().into(holder.pic)

//            viewholder.itemView.setOnClickListener{
//                if(onCLickListener != null){
//                    onCLickListener !!.onClick(position,buddy)
//                }
//            }
        }
        else{
            val viewholder=holder as buddyViewHolder
            viewholder.name.text = buddy.name
            viewholder.gender.text = buddy.gender
            //       pic ka khel
            Glide.with(viewholder.pic.context).load(buddy.photo).circleCrop().into(holder.pic)
            viewholder.itemView.setOnClickListener{
                if(onCLickListener != null){
                    onCLickListener !!.onClick(position,buddy)
                }
            }
        }

    }
    fun setOnClickListener(onClickListener: OnClickListener){
        this.onCLickListener=onClickListener
    }
    interface OnClickListener{
        fun onClick(position: Int,model: buddy)

    }


}
