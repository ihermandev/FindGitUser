package com.herman.illia.findgituser.network

import android.content.Context

/**
 * Created by illia on 8/27/2019.
 */
class RetrofitApiUtils {
    companion object {
        fun getApiService(context: Context): GithubApiService? {
            return RetrofitClientBuilder.getClient(context = context)?.create(GithubApiService::class.java)
        }
    }
}