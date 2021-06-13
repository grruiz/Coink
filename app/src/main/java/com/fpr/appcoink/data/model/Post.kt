package com.fpr.appcoink.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    val id_post: String = UUID.randomUUID().toString(),
    val profile_picture:String = "",
    val profile_name:String = "",
    @ServerTimestamp
    var post_timestamp:Date?= null,
    val post_desc_expense:String = "",
    val post_expense:String = "",
    val uid:String = ""
                )