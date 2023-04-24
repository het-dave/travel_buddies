package com.example.travelbuddies

//import okhttp3.*
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import java.io.IOException
//
//class NotificationSender {
//    private val client = OkHttpClient()
//    private val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
//    private val url = "https://fcm.googleapis.com/fcm/send"
//    private val serverKey = "AAAAKseDUig:APA91bFmnJwpQtXFIAFmA4-OD8vAh5BbZvOY7nM9jJOZdjaYY4W-3gInSHXaOY1BHzBYS8gpxw_oBVqOS83b0gk2czpo1uEFx2r61B9oMcD_kvnzztt_4iOkg6D_UORyRYKGtbVvU3VW"
//
//    fun sendNotification( token: String, title: String, body: String) {
//        // Construct the message as a JSON string
//        val message = """
//            {
//                "token": "$token",
//                "notification": {
//                    "title": "$title",
//                    "body": "$body"
//                }
//            }
//            """
//
//        // Create the HTTP request
//        val request = Request.Builder()
//            .url(url)
//            .post(RequestBody.create(JSON, message))
//            .addHeader("Authorization", "key=$serverKey")
//            .build()
//
//        // Send the HTTP request
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                // Handle the failure
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                // Handle the response
//            }
//            })
//        }
//}