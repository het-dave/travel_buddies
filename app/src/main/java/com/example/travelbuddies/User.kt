package com.example.travelbuddies

import com.google.firebase.database.DataSnapshot

data class User(val name: String? = null, val email : String?=null, val photo : String?=null, val uid : String?=null, val toogle : Boolean=false, val gender : String?=null, val paired : Boolean=false, val friend_uid : String?=null, val PhoneNo: String? = null, val token : String?=null,
                var friends: Any?=null)
{



}
