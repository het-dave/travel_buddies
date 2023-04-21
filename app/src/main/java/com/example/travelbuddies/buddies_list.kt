package com.example.travelbuddies

import android.app.Dialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbuddies.databinding.BuddiesListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class buddies_list : AppCompatActivity() {
    lateinit var binding : BuddiesListBinding
    lateinit var database: DatabaseReference
    var buddieslist = ArrayList<buddy>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BuddiesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= Firebase.database.reference
        val uid=intent.getStringExtra("uid")
        buddieslist=ArrayList<buddy>()
        database.child("users")

//        check if the user is on or off



        val BuddyAdapter=BuddyAdapter(this,buddieslist)
        binding.recyclerView.adapter =BuddyAdapter
//        applying OnClickListner to our adapter
        BuddyAdapter.setOnClickListener(object: BuddyAdapter.OnClickListener{
            override fun onClick(position :Int , model : buddy){
                Toast.makeText(this@buddies_list, model.name.toString(), Toast.LENGTH_SHORT).show()
                    var dialog = Dialog(this@buddies_list)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.send_request_popup)
                    dialog.findViewById<TextView>(R.id.reallysend).text="Send a request to ${model.name}?"
                    dialog.findViewById<Button>(R.id.yes).setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                    })
                    dialog.findViewById<Button>(R.id.no).setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                    })

                    dialog.show()

            }
        })

//        loop through the database and add the users to the list
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(this@buddies_list, "on data change", Toast.LENGTH_SHORT).show()
                buddieslist.clear()
                for (data in snapshot.children) {
                    Log.d("data",data.toString())
                    Log.d("data",data.child("toogle").toString())
                    if(data.key==uid || data.child("toogle").value.toString()=="false"){
                        continue
                    }
                        val name = data.child("name").value.toString()
                        Log.d("name", name)
                        val email = data.child("email").value.toString()
                        val photo = data.child("photo").value.toString()
                        buddieslist.add(buddy(name, email))
                        Log.d("buddieslist", buddieslist.toString())


                }
                binding.recyclerView.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@buddies_list, "Error", Toast.LENGTH_SHORT).show()
            }

        })

    }
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val uid=intent.getStringExtra("uid")
//        val intent : Intent = Intent(this, Toggler::class.java)
//        intent.putExtra("uid",uid)
//        //         set switch to on if toogle is true
//       val switch=Toggler().findViewById<Switch>(R.id.idSwitch)
//        switch.isChecked=false
//        database.child("users").child(uid!!).child("toogle").get().addOnSuccessListener {
//            it.value == false
//
//
//        }
//        startActivity(intent)
//    }

}


//findViewById<ImageView>(R.id.imageView1).setOnClickListener {
//    Toast.makeText(this, "clicked image", Toast.LENGTH_SHORT).show()
//    var dialog = Dialog(this)
//    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//    dialog.setCancelable(false)
//    dialog.setContentView(R.layout.friend_request_popup)
//    dialog.btnAccept.setOnClickListener {
//        Toast.makeText(this, "clicked accept", Toast.LENGTH_SHORT).show()
//        dialog.dismiss()
//    }
//    dialog.show()
//}