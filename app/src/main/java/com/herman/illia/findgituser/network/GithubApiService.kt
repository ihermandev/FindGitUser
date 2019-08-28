package com.herman.illia.findgituser.network

import com.herman.illia.findgituser.model.UserModel
import com.herman.illia.findgituser.utils.Constant.GET_USER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by illia on 8/27/2019.
 */
interface GithubApiService {
    @GET(GET_USER)
    fun getUser(@Path("username") username: String): Call<UserModel>
}