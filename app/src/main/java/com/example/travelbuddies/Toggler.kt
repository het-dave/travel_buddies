package com.example.travelbuddies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.graphics.toColorInt
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Toggler : AppCompatActivity(),OnNavigationItemSelectedListener{
    lateinit var switch: Switch
    var actionBarDrawerToggle :ActionBarDrawerToggle ?=null;
    var uid:String?=null


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
override fun onStart() {

    super.onStart()

        setContentView(R.layout.toggle)
//        change status bar color
        window.statusBarColor = "#F8B500".toColorInt()

//            drawer code
    val drawer=findViewById<DrawerLayout>(R.id.drawerLayout)
    actionBarDrawerToggle=ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
    drawer.addDrawerListener(actionBarDrawerToggle!!)
    actionBarDrawerToggle!!.syncState()
//    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
     val navigation=findViewById<NavigationView>(R.id.navigationView)

    navigation.setNavigationItemSelectedListener(this)




        val database= Firebase.database.reference
//         get uid
        uid = Firebase.auth.currentUser?.uid
        Toast.makeText(this, "uid is $uid", Toast.LENGTH_SHORT).show()
        switch = findViewById(R.id.idSwitch)

//         set switch to on if toogle is true
    database.child("users").child(uid!!).child("toogle").setValue(false)

//
//        on switch on set toggle to on
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                database.child("users").child(uid!!).child("toogle").setValue(true)
//                val intent : Intent = Intent(this, buddies_list::class.java)
//                intent.putExtra("uid",uid)
//                startActivity(intent)
                val name=intent.getStringExtra("name")
                val email=intent.getStringExtra("email")
                val photo=intent.getStringExtra("photo")
                val token=intent.getStringExtra("token")
                val gender=intent.getStringExtra("gender")
                val intent : Intent = Intent(this, buddies_list::class.java)
                intent.putExtra("name",name)
                intent.putExtra("email",email)
                intent.putExtra("photo",photo)
                intent.putExtra("uid",uid)
                intent.putExtra("token",token)
                intent.putExtra("gender",gender)
                startActivity(intent)

            } else {
                database.child("users").child(uid!!).child("toogle").setValue(false)


            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about->{
                Toast.makeText(this,"Anout us",Toast.LENGTH_SHORT).show()
            }
            R.id.editDetails->{

                val name=intent.getStringExtra("name")
                val email=intent.getStringExtra("email")
                val photo=intent.getStringExtra("photo")
                val token=intent.getStringExtra("token")
                val gender=intent.getStringExtra("gender")
                val intent : Intent = Intent(this, Profiler::class.java)
                intent.putExtra("name",name)
                intent.putExtra("email",email)
                intent.putExtra("photo",photo)
                intent.putExtra("uid",uid)
                intent.putExtra("token",token)
                intent.putExtra("gender",gender)
                startActivity(intent)
            }
            R.id.logout->{
//                    logout


                val intent : Intent = Intent(this, MainActivity::class.java)
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)

                finish()
//                terminate app
//                finishAffinity()
            }
        }
        return true
    }
}