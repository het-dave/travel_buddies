package com.example.travelbuddies
//
//import android.content.pm.PackageManager
//import android.util.Log
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    companion object {
//        const val CHANNEL_ID = "TravelBuddies"
//    }
//
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Toast.makeText(this, "onMessageReceived active", Toast.LENGTH_SHORT).show()
//        val notificationData = remoteMessage.notification
//        val notificationTitle = notificationData?.title
//        val notificationBody = notificationData?.body
//        val notificationId = remoteMessage.messageId?.toInt()
////        val notificationId = System.currentTimeMillis().toInt()
//
//        if (notificationTitle != null && notificationBody != null) {
//
//            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
//                .setContentTitle(notificationTitle)
//                .setContentText(notificationBody)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
////                .setSmallIcon(R.drawable.notification_icon)
//
//
//            if (ActivityCompat.checkSelfPermission(
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
//                return
//            }
//            if (notificationId != null) {
//                val notificationManager = NotificationManagerCompat.from(applicationContext)
//                notificationManager.notify(notificationId, notification.build())
//                Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show()
//            }
//            }
//    }
//
//
//}