package com.fpr.appcoink.presentation.postuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.domain.postuser.PostUserRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PostUserViewModel(private val postUser: PostUserRepo): ViewModel() {

    fun uploadPost(post_desc_expense: String,post_expense:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {
            emit(Result.Success(postUser.uploadPost(post_desc_expense,post_expense)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}


class PostUserodelFactory(private val repo: PostUserRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PostUserRepo::class.java).newInstance(repo)
    }

}