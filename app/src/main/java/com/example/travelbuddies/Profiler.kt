package com.example.travelbuddies

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase
import okhttp3.internal.cache.DiskLruCache

class Profiler:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    var uid:String?=null
    var friends:DataSnapshot?=null
    var token:String?=null

//    override fun on(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
    override fun onStart() {

        super.onStart()
        setContentView(R.layout.profile)
//        Toast.makeText(this, "9", Toast.LENGTH_SHORT).show()


        auth = FirebaseAuth.getInstance()


//    if email ends with iitj.ac.in
//    if(email!!.endsWith("iitj.ac.in")){
//        Toast.makeText(this, "email ends with iitj.ac.in", Toast.LENGTH_SHORT).show()
//    }
//    else{
//        Toast.makeText(this, "email does not end with iitj.ac.in", Toast.LENGTH_SHORT).show()
//
//        val intent=Intent(this,MainActivity::class.java)
//        startActivity(intent)
////        auth.signOut()
//        finish()
//    }
//

    database= Firebase.database.reference
    var name=intent.getStringExtra("name")
    var photo=intent.getStringExtra("photo")
    var email=intent.getStringExtra("email")
    var token=intent.getStringExtra("token")
    var phone=intent.getStringExtra("phone")
//    Toast.makeText(this, "photo is $photo", Toast.LENGTH_SHORT).show()
    uid=auth.currentUser?.uid
    findViewById<TextView>(R.id.EmailID).text=email
//        Toast.makeText(this, findViewById<TextView>(R.id.EmailID).text, Toast.LENGTH_SHORT).show()
    findViewById<TextView>(R.id.Name).text=name
    Glide.with(this).load(photo).circleCrop().into(findViewById<ImageView>(R.id.Profile_Photo))

    database.child("users").child(auth.currentUser?.uid!!).get().addOnSuccessListener {
//        println("HAHAHAHAHAHAHHAHAHAHAHHAHAHAHAHAHHAHAHAHAH");
//        name=it.child("name").value.toString()
        if(it.child("name").value!=null && name==null){
            name=it.child("name").value.toString()
            findViewById<TextView>(R.id.Name).text=name
        }

        if(photo==null && it.child("photo").value!=null){
            photo=it.child("photo").value.toString()
            Glide.with(this).load(photo).circleCrop().into(findViewById<ImageView>(R.id.Profile_Photo))
        }

        if(it.child("email").value!=null && email==null){
            email=it.child("email").value.toString()
            findViewById<TextView>(R.id.EmailID).text=email
        }


        if(it.child("phoneNo").value!=null){
            phone=it.child("phoneNo").value.toString()
            findViewById<TextView>(R.id.PhoneNo).text=phone
        }

        //        get the uid of the user
        if(token==null && it.child("token").value!=null)
            token=it.child("token").value.toString()



    }


//        Toast.makeText(this, "bhagoo uid is $uid", Toast.LENGTH_SHORT).show()

//        check if email ends with @iij.ac.in
//        val email1=email!!.split("@")

//        Toast.makeText(this, email1[1], Toast.LENGTH_SHORT).show()





// database
//        Toast.makeText(this, "before get reference", Toast.LENGTH_SHORT).show()

//        var friendss=database.child("users").child(uid!!).child("uid").database





       database.child("users").child(uid!!).child("friends").get().addOnSuccessListener {
           println("HAHAHAHAHAHAHHAHAHAHAHHAHAHAHAHAHHAHAHAHAH");

           val user=User(name,email,photo,uid,false, gender = "not", paired = false, friend_uid = "", PhoneNo = "",token=token)
            user.friends=it.value
           println("friends are ${user.friends}")


           database.child("users").child(uid!!).setValue(user)
//           if(user.friends==null){
//               database.child("users").child(uid!!).child("friends").child("no friends").setValue("no friends")
//           }
        }



//        getting data


//        gender dropdown
        val genders= arrayOf("Male","Female","Other")
        val arrayAdapter = ArrayAdapter(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genders)
        val spinner=findViewById<Spinner>(R.id.GenderBG)
        spinner.adapter=arrayAdapter
//        Toast.makeText(this, "after adapter", Toast.LENGTH_SHORT).show()
        spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                findViewById<TextView>(R.id.GenderText).text=genders[position]
//                Toast.makeText(this@Profiler, "on item selected", Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                findViewById<TextView>(R.id.GenderText).text="none"
//                Toast.makeText(this@Profiler, "nothing selected", Toast.LENGTH_SHORT).show()
            }

        }




        findViewById<ImageView>(R.id.Save).setOnClickListener {

            val phoneno= findViewById<TextView>(R.id.PhoneNo).text.toString()

            val gender=findViewById<TextView>(R.id.GenderText).text.toString()

//            val user=User(name,email,photo,uid,false, gender = gender , paired = false, friend_uid = "", PhoneNo =phoneno,token = token )
//
//
//

            database.child("users").child(uid!!).child("phoneNo").setValue(phoneno)
            database.child("users").child(uid!!).child("gender").setValue(gender)


            val intent : Intent = Intent(this, Toggler::class.java)
            intent.putExtra("uid",uid)
            intent.putExtra("email",email)
            intent.putExtra("name",name)
            intent.putExtra("photo",photo)
            intent.putExtra("token",token)
            intent.putExtra("gender",gender)


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