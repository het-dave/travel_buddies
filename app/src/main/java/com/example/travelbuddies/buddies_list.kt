package com.example.travelbuddies

import android.app.Dialog
import android.app.Notification
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import com.example.travelbuddies.SendNotificationPack.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.travelbuddies.SendNotificationPack.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random
//impoimport com.example.travelbuddies.SendNotificationPack.*


class buddies_list : AppCompatActivity() {
    lateinit var binding : BuddiesListBinding
    lateinit var database: DatabaseReference
    var buddieslist = ArrayList<buddy>()
    var friends=ArrayList<buddy>()
    lateinit var apiService: APIService
    val auth = FirebaseAuth.getInstance()

    fun recieveNotification(title: String,message: String){
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(com.example.travelbuddies.R.layout.friend_request_popup)
        dialog.setCancelable(true)
        var title1=dialog.findViewById<TextView>(com.example.travelbuddies.R.id.textView)
//        var message1=dialog.findViewById<TextView>(R.id.message)
        title1.text=title
//        message1.text=message
        dialog.show()
    }

    private fun sendNotification(usertoken:String,title: String,message: String){
        var data= Data(title,message)
        var sender: NotificationSender = NotificationSender(data,usertoken)
        apiService.sendNotifcation(sender)!!.enqueue(object : Callback<MyResponse?> {

            override fun onResponse(call: Call<MyResponse?>, response: Response<MyResponse?>) {
                if (response.code() === 200) {
                    if (response.body()!!.success !== 1) {
                        Toast.makeText(this@buddies_list, "Request Failed,try again", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<MyResponse?>, t: Throwable?) {

            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BuddiesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= Firebase.database.reference
        val uid = auth.currentUser?.uid


//        check if the user is on or off
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService::class.java)


        val BuddyAdapter=BuddyAdapter(this,buddieslist,uid.toString())
        binding.recyclerView.adapter =BuddyAdapter
//        applying OnClickListner to our adapter
        database.child("users").child(uid!!).child("requests").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value!=null){
                    var dialog = Dialog(this@buddies_list)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.friend_request_popup)
                    var title=dialog.findViewById<TextView>(R.id.textView)
                    var accept=dialog.findViewById<Button>(R.id.accept)
                    var reject=dialog.findViewById<Button>(R.id.reject)
                    database.child("users").child(snapshot.value.toString()!!).get().addOnSuccessListener {
                        title.text="You have a friend request from ${it.child("name").value.toString()}(${it.child("gender").value.toString()})"
                    }


//                    title.text="You have a friend request from ${snapshot.value.toString()}"
                    accept.setOnClickListener(View.OnClickListener {
                        database.child("users").child(uid!!).child("friends").child(snapshot.value.toString()).setValue("true")
                        database.child("users").child(snapshot.value.toString()).child("friends").child(uid!!).setValue("true")
                        database.child("users").child(uid!!).child("requests").removeValue()
                        dialog.dismiss()
                    })
                    reject.setOnClickListener(View.OnClickListener {
                        database.child("users").child(uid!!).child("requests").removeValue()
                        dialog.dismiss()
                    })
                    dialog.show()
                }


//                database.child("users").child(uid!!).child("friends").child(snapshot.value.toString()).setValue("true")
//                database.child("users").child(snapshot.value.toString()).child("friends").child(uid!!).setValue("true")

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        BuddyAdapter.setOnClickListener(object: BuddyAdapter.OnClickListener{
            override fun onClick(position :Int , model : buddy){
//                Toast.makeText(this@buddies_list, model.name.toString(), Toast.LENGTH_SHORT).show()
                    var dialog = Dialog(this@buddies_list)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(false)
                database.child("users").child(uid!!).get().addOnSuccessListener {
                    val gender=it.child("gender").value.toString()
                    val name=it.child("name").value.toString()
                    dialog.setContentView(R.layout.send_request_popup)
                    dialog.findViewById<TextView>(R.id.reallysend).text="Send a request to ${model.name}?"
                    dialog.findViewById<Button>(R.id.yes).setOnClickListener(View.OnClickListener {
//                        Toast.makeText(this@buddies_list, "model token is ${model.token}", Toast.LENGTH_SHORT).show()

                        sendNotification(model.token.toString(),"Travel Buddies","${name} (${gender}) wants to be your buddy")
                        database.child("users").child(model.uid).child("requests").setValue(uid)
                        dialog.dismiss()
                    })
                    dialog.findViewById<Button>(R.id.no).setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                    })

                    dialog.show()
                }


            }
        })

//        loop through the database and add the users to the list
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Toast.makeText(this@buddies_list, "on data change", Toast.LENGTH_SHORT).show()
                buddieslist.clear()
                for (data in snapshot.children) {
                    Log.d("data",data.toString())
                    Log.d("data",data.child("toogle").toString())
                    if(data.key==uid || data.child("toogle").value.toString()=="false"){
                        continue
                    }
//                    database.child("users").child(uid).child("friends").addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            if(snapshot.hasChild(buddy.uid)){
//                                buddy.friend=true
//                                println("true")
//                            }
//                            else{
////                    buddy.friend=false
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//
//                    })
//                    println("buddy friend is ${buddy.friend}")
                    var friend=false;
                    println("${data.child("friends")}dekho dekho kya wo ped h ${uid!!}}")
                    if(data.child("friends").hasChild(uid!!)){
                        friend = true
                    }

                        val email = data.child("email").value.toString()
                        val friend_uid = data.child("friend_uid").value.toString()
                        val name = data.child("name").value.toString()
                        val paired = data.child("paired").value.toString().toBoolean()
                        val photo = data.child("photo").value.toString()
                        val toggle = data.child("toogle").value.toString().toBoolean()
                        val fuid   = data.child("uid").value.toString()
                        val gender = data.child("gender").value.toString()
                        val token = data.child("token").value.toString()
                        val timestamp = data.child("timestamp").value.toString()
                        val phone = data.child("phoneNo").value.toString()

                        buddieslist.add(buddy(email,friend_uid,name,paired,photo,toggle,fuid,gender,token,timestamp, friend =friend ,phone))
                        Log.d("buddieslist", buddieslist.toString())

                }
                binding.recyclerView.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@buddies_list, "Error", Toast.LENGTH_SHORT).show()
            }

        })


        findViewById<ImageButton>(R.id.imageButton2).setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(this, Toggler::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)
        })


    }
    override fun onBackPressed() {
        super.onBackPressed()
        val uid=intent.getStringExtra("uid")
        val intent : Intent = Intent(this, Toggler::class.java)
        intent.putExtra("uid",uid)

        startActivity(intent)
    }

//    private fun UpdateToken(){
//        var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
//        var refreshToken:String= FirebaseMessaging.getInstance().token.toString()
//        var token:Token=Token(refreshToken)
//        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).setValue(token)
//    }

// createNotification(context, "My Title", "My Message")

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

//button.setOnClickListener {
//    val notificationSender = NotificationSender()
//    val token = "FCM_TOKEN" // Replace with the FCM token of the recipient device
//    val title = "My Notification"
//    val body = "This is a notification from my app"
//    notificationSender.sendNotification(token, title,body)
//    }

//sendNotificationButton.setOnClickListener {
//    val notificationSender = NotificationSender()
//    notificationSender.sendNotification("RECEIVER_DEVICE_TOKEN", "Notification Title", "NotificationBody")
//}

