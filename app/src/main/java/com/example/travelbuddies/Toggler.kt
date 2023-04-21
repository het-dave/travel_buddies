package com.example.travelbuddies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Toggler : AppCompatActivity() {
    lateinit var switch: Switch

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
override fun onStart() {

    super.onStart()
        setContentView(R.layout.toggle)
        val database= Firebase.database.reference
        val uid=intent.getStringExtra("uid")
        Toast.makeText(this, "uid is $uid", Toast.LENGTH_SHORT).show()
        switch = findViewById(R.id.idSwitch)

//         set switch to on if toogle is true
    database.child("users").child(uid!!).child("toogle").setValue(false)

//
//        on switch on set toggle to on
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                database.child("users").child(uid!!).child("toogle").setValue(true)
                val intent : Intent = Intent(this, buddies_list::class.java)
                intent.putExtra("uid",uid)
                startActivity(intent)

            } else {
                database.child("users").child(uid!!).child("toogle").setValue(false)


            }

        }
    }
}