package com.herman.illia.findgituser.network

import android.content.Context
import com.commonsware.cwac.netsecurity.OkHttp3Integrator
import com.commonsware.cwac.netsecurity.TrustManagerBuilder
import com.herman.illia.findgituser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by illia on 8/27/2019.
 */
object RetrofitClientBuilder {
    private var mRetrofit: Retrofit? = null

    fun getClient(context: Context): Retrofit? {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(getLoggerOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return mRetrofit
    }

    private fun getLoggerOkHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okb = OkHttpClient.Builder()
        okb.addInterceptor(logging)
        okb.followRedirects(false)
        okb.followSslRedirects(false)
        OkHttp3Integrator.applyTo(TrustManagerBuilder().withManifestConfig(context), okb)
        return okb.build()
    }
}