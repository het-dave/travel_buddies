package com.example.travelbuddies

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Profiler:AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        auth = FirebaseAuth.getInstance()
        val email=intent.getStringExtra("email")
        val name=intent.getStringExtra("name")
        val photo=intent.getStringExtra("photo")

        findViewById<TextView>(R.id.textView).text=email

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}