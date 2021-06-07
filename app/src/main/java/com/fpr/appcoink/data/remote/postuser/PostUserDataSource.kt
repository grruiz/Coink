package com.fpr.appcoink.data.remote.postuser

import com.fpr.appcoink.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PostUserDataSource {
    suspend fun uploadPost(post_desc_expense: String, post_expense: String){
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()

        user?.let {
            it.displayName?.let{ displayName ->
                FirebaseFirestore.getInstance().collection(user.email.toString()).add(
                    Post(
                    profile_name = displayName,
                    profile_picture = it.photoUrl.toString(),
                    post_desc_expense = post_desc_expense,
                    post_expense = post_expense,
                    uid = user.uid)
                )
            }
        }
    }
}