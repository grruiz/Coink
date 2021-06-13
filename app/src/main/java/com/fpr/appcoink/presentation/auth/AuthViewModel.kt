package com.fpr.appcoink.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.domain.auth.AuthRepo
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class AuthViewModel(private val repo:AuthRepo):ViewModel() {
    fun signIn(email:String,password:String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.signIn(email,password)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun signUp(email:String,password:String,username:String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.signUp(email,password,username)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap,username: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.updateProfile(imageBitmap,username)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun  getUser() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.getUser()))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun getPostDocument(documentReference: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try{
            emit(Result.Success(repo.getPostDocument(documentReference)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
}

class AuthViewModelFactory(private val repo:AuthRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }

}