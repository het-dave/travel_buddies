package com.example.travelbuddies

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Profiler:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        Toast.makeText(this, "9", Toast.LENGTH_SHORT).show()

        auth = FirebaseAuth.getInstance()
        var email=intent.getStringExtra("email")
        val name=intent.getStringExtra("name")
        val photo=intent.getStringExtra("photo")
        //        get the uid of the user
        val uid=auth.currentUser?.uid


//        check if email ends with @iij.ac.in
        val email1=email!!.split("@")

//        Toast.makeText(this, email1[1], Toast.LENGTH_SHORT).show()



        findViewById<TextView>(R.id.EmailID).text=email
        findViewById<TextView>(R.id.Name).text=name
        Glide.with(this).load(photo).circleCrop().into(findViewById<ImageView>(R.id.Profile_Photo))

// database
        Toast.makeText(this, "before get reference", Toast.LENGTH_SHORT).show()
        database= Firebase.database.reference
        Toast.makeText(this, "after get ref", Toast.LENGTH_SHORT).show()
        val user=User(name,email,photo,uid,false)




        database.child("users").child(uid!!).setValue(user)

//        getting data
        database.child("users").child(uid).child("toogle").get().addOnSuccessListener {
            Toast.makeText(this, "toogle is ${it.value}", Toast.LENGTH_SHORT).show()
        }




        findViewById<ImageView>(R.id.Save).setOnClickListener {


            val intent : Intent = Intent(this, Toggler::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)


        }
    }

}

//// Create a new PopupWindow object
//PopupWindow popupWindow = new PopupWindow(context);
//
//// Inflate the layout for your custom pop up
//View view = LayoutInflater.from(context).inflate(R.layout.popup_window, null);
//
//// Set the view of your PopupWindow object to the inflated layout
//popupWindow.setContentView(view);

//Back Button
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
//
//    // set toolbar as support action bar
//    setSupportActionBar(toolbar)
//
//    // show back button on toolbar
//    supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//    // on back button press, it will navigate to parent activity
//    toolbar.setNavigationOnClickListener {
//        onBackPressed()
//    }
//}

//Add Sidebar Menu
//https://code.tutsplus.com/tutorials/how-to-code-a-navigation-drawer-in-an-android-app--cms-30263