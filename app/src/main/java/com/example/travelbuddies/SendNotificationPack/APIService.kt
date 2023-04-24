package com.example.travelbuddies.SendNotificationPack

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

public interface APIService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAKseDUig:APA91bFmnJwpQtXFIAFmA4-OD8vAh5BbZvOY7nM9jJOZdjaYY4W-3gInSHXaOY1BHzBYS8gpxw_oBVqOS83b0gk2czpo1uEFx2r61B9oMcD_kvnzztt_4iOkg6D_UORyRYKGtbVvU3VW" // Your server key refer to video for finding your server key
    )
    @POST("fcm/send")
    open fun sendNotifcation(@Body body: NotificationSender?): Call<MyResponse?>?
}