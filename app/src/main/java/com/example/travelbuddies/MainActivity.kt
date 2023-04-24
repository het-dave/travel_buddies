package com.example.travelbuddies

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient
    var token:String?=null
//    private val NOTIFICATION_PERMISSION_CODE = 123
//
//
//    private fun requestNotificationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_NOTIFICATION_POLICY
//            ) == PackageManager.PERMISSION_GRANTED
//        ) return
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.ACCESS_NOTIFICATION_POLICY
//            )
//        ) {
//        }
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf<String>(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
//            NOTIFICATION_PERMISSION_CODE
//        )
//    }

    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            Toast.makeText(this, "Signed Out Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseApp.initializeApp(this)

        super.onCreate(savedInstanceState)

//        check if logged in
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this, Toggler::class.java))
            finish()
        }
        setContentView(R.layout.activity_main)
//        setContentView(R.layout.profile)
//        val NOTIFICATION_PERMISSION_CODE = 123
//        requestNotificationPermission()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "TravelBuddies"
            val channelName = "TravelBuddies"

            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance)

            channel.description = "TravelBuddies"

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                    Log.d("MyApp", "FCM registration token: $token")

                } else {
                    Log.w("MyApp", "Failed to retrieve FCM registration token", task.exception)
                    }
                }





        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

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

//                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>?) {

        if (task != null) {
            if (task.isSuccessful) {
                val account : GoogleSignInAccount? = task.result
                if (account != null) {
                    updateUI(account)

//                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
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
//                if email dosent ends with @iitj.ac.in then signout
                if (!account.email!!.endsWith("@iitj.ac.in")) {
                    Toast.makeText(this, "Please use IITJ email", Toast.LENGTH_SHORT).show()
                    googleSignInClient.signOut()
                    return@addOnCompleteListener
                }
                intent.putExtra("photo", account.photoUrl.toString())
                intent.putExtra("token", token)


                Toast.makeText(this,token , Toast.LENGTH_SHORT).show()



                startActivity(intent)
            } else {
//                toast error
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
//                description = "My Notification Channel Description"
//                enableLights(true)
//                lightColor = Color.RED
//                enableVibration(true)
//                vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
//            }
//            val notificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }

//    }

    fun createNotification(context: Context, title: String, message: String) {
        // Define the Notification ID
        val notificationId = 1

        // Create the Notification Builder
        val builder = NotificationCompat.Builder(context, "my_channel_id")
//            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get the Notification Manager
        val notificationManager = NotificationManagerCompat.from(context)

        // Send the Notification
        if (ActivityCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }





}