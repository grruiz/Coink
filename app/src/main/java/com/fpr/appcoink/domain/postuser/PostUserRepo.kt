package com.fpr.appcoink.domain.postuser

interface PostUserRepo {
    suspend fun uploadPost(post_desc_expense: String,post_expense:String)
}