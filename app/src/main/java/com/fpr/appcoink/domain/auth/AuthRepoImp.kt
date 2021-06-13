package com.fpr.appcoink.domain.auth

import android.graphics.Bitmap
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot


class AuthRepoImp(private val dataSource:AuthDataSource): AuthRepo {

    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)

    override suspend fun signUp(email: String, password: String, username: String): FirebaseUser? =
        dataSource.signUp(email, password, username)

    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) =
        dataSource.updateUserProfile(imageBitmap, username)

    override suspend fun getUser(): FirebaseUser? =
        dataSource.getUser()

    override suspend fun getPostDocument(documentReference: String)  =
        dataSource.getPostDocument(documentReference)


}