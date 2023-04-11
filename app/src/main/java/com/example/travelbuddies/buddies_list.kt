package com.example.travelbuddies

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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




        binding.recyclerView.adapter = BuddyAdapter(this,buddieslist)
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

}