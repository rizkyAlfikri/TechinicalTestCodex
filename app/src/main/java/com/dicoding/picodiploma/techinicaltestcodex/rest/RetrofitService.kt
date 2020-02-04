package com.dicoding.picodiploma.techinicaltestcodex.rest

import com.dicoding.picodiploma.techinicaltestcodex.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private fun init(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createRetrofit(apiEndpoint: Class<T>): T {
        return init().create(apiEndpoint)
    }
}