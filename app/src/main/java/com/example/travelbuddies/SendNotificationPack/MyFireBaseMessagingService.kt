package com.example.travelbuddies.SendNotificationPack

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.content.Intent
import android.view.Window
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.travelbuddies.NotificationActivity
import com.example.travelbuddies.Toggler
import com.example.travelbuddies.buddies_list
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.travelbuddies.*

class MyFireBaseMessagingService:FirebaseMessagingService(){
    private lateinit var title:String;
    private lateinit var message:String;
    private val NOTIFICATION_PERMISSION_CODE = 123
    @SuppressLint("SuspiciousIndentation")
    override fun onMessageReceived(@NonNull remoteMessage:RemoteMessage){

//        Toast.makeText(this, "onMessageReceived active", Toast.LENGTH_SHORT).show()

        val notificationId = System.currentTimeMillis().toInt()
        super.onMessageReceived(remoteMessage)
        title = remoteMessage.getData()?.get("Title").toString()
        message = remoteMessage.getData().get("Message").toString()
//        val builder =NotificationCompat.Builder(applicationContext)
//            .setSmallIcon(android.R.drawable.stat_sys_download)
//            .setContentTitle(title)
//            .setContentText(message)

//        call function in buddies_list.kt to call the notification
        val builder = NotificationCompat.Builder(applicationContext, "TravelBuddies")
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_dialog_alert)
        val notificationIntent = Intent(this, buddies_list::class.java)
        val updatedPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            notificationIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT // setting the mutability flag
//
        )
        builder.setContentIntent(updatedPendingIntent)

//        Toast.makeText(this, "onMessageReceived active", Toast.LENGTH_SHORT).show()
//                .setSmallIcon(R.drawable.notification_icon)
//        val manager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(0, builder.build())

//        if (ActivityCompat.checkSelfPermission(
//                    this,
//                    "android.permission.POST_NOTIFICATIONS"
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
////                pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//
//                System.out.println("permission nahi diya")
//
//                return
//            }
            if (notificationId != null) {
                System.out.println("permission nahi diya")
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(notificationId, builder.build())

            }
            }
//    fun recieveNotification(title: String,message: String){
//        var dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.setContentView(com.example.travelbuddies.R.layout.friend_request_popup)
//        dialog.setCancelable(true)
//        var title1=dialog.findViewById<TextView>(com.example.travelbuddies.R.id.textView)
////        var message1=dialog.findViewById<TextView>(R.id.message)
//        title1.text=title
////        message1.text=message
//        dialog.show()
//    }

}

