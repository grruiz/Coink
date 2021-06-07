package com.fpr.appcoink.domain.postuser

import com.fpr.appcoink.data.remote.postuser.PostUserDataSource

class PostUserRepoImp(private val dataSource: PostUserDataSource): PostUserRepo {
    override suspend fun uploadPost(post_desc_expense: String, post_expense: String) {
        dataSource.uploadPost(post_desc_expense,post_expense)
    }

}