package com.fpr.appcoink.data.remote.home

import android.util.Log
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLastestPosts(): Result<List<Post>> {
        val user = FirebaseAuth.getInstance().currentUser
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(user?.email.toString()).get().await()
        for (post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {
                it.apply { querySnapshot.documents }
                it.apply { post_timestamp = post.getTimestamp("post_timestamp", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)?.toDate() }
                postList.add(it)
            }
        }
        return Result.Success(postList)
    }
}