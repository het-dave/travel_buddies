package com.example.travelbuddies

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setContentView(R.layout.profile)



        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {

            signInGoogle()

        }


    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)

                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>?) {

        if (task != null) {
            if (task.isSuccessful) {
                val account : GoogleSignInAccount? = task.result
                if (account != null) {
                    updateUI(account)

                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
                }

//                Toast.makeText(this, "1234", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
            }
        }

        else {
            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val intent : Intent = Intent(this, Profiler::class.java)
                intent.putExtra("name", account.displayName)
                intent.putExtra("email", account.email)
                intent.putExtra("photo", account.photoUrl.toString())

                Toast.makeText(this, "5", Toast.LENGTH_SHORT).show()



                startActivity(intent)
            } else {
//                toast error
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

}