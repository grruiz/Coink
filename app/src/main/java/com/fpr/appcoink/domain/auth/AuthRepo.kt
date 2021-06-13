package com.fpr.appcoink.domain.auth

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot

interface AuthRepo {
    suspend fun signIn(email:String,password:String): FirebaseUser?
    suspend fun signUp(email: String, password: String, username: String): FirebaseUser?
    suspend fun updateProfile(imageBitmap: Bitmap, username:String)
    suspend fun getUser():FirebaseUser?
    suspend fun getPostDocument(documentReference: String)
}