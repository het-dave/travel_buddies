package com.example.travelbuddies

import java.sql.Timestamp

data class buddy(
    val email: String = " ",
    val friend_list: String = " ",
    val name: String = " ",
    val paired: Boolean = false,
    val photo: String = " ",
    val toggle: Boolean = false,
    val uid: String = " ",
    val gender: String=" ",
    val token:String?=null,
    val timestamp:String?=null,
    var friend:Boolean=false,
    val phone: String = " ",

    )
