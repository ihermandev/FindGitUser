package com.herman.illia.findgituser.network

import android.content.Context
import android.util.Log
import com.herman.illia.findgituser.model.ErrorModel
import com.herman.illia.findgituser.model.UserModel
import com.herman.illia.findgituser.network.listener.RequestUserListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by illia on 8/27/2019.
 */
object ServerClient {
    private var apiService: GithubApiService? = null
    private var requestUserModel: Call<UserModel>? = null
    private var mRequestUserListener: RequestUserListener? = null

    fun initServerClient(context: Context) {
        this.apiService = RetrofitApiUtils.getApiService(context)
    }

    fun requestUser(username: String) {
        requestUserModel = apiService?.getUser(username)
        requestUserModel?.enqueue(GetUserCallback())
    }

    internal class GetUserCallback : Callback<UserModel> {
        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            Log.d(
                ServerClient.javaClass.name,
                "GetUserCallback : FAILURE : ${t.message} ${t.localizedMessage}"
            )
            val errorModel =
                ErrorModel(errorTitleText = "ERROR", errorMessageText = t.message, exception = t)
            mRequestUserListener?.errorUserListener(errorModel)
        }

        override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
            Log.d(ServerClient.javaClass.name, "GetUserCallback : SUCCESS : ${response.code()}")
            when (response.code()) {
                200 -> {
                    mRequestUserListener?.responseUserListener(true, response.body(), 200)
                }
                else -> {
                    mRequestUserListener?.responseUserListener(
                        false,
                        response.body(),
                        response.code()
                    )
                }
            }

        }
    }

    fun registerGetUserListener(listener: RequestUserListener) {
        this.mRequestUserListener = listener
    }

}