package com.dicoding.picodiploma.techinicaltestcodex.di

import android.app.Application
import com.dicoding.picodiploma.techinicaltestcodex.data.source.MainDataRepository
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.room.UserStoryDatabase
import com.dicoding.picodiploma.techinicaltestcodex.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.techinicaltestcodex.rest.ApiEndpoint
import com.dicoding.picodiploma.techinicaltestcodex.rest.RetrofitService

class Injection {
    companion object{
        private val apiEndpoint = RetrofitService.createRetrofit(ApiEndpoint::class.java)

        fun provideService(application: Application): MainDataRepository? {
            val database = UserStoryDatabase.getInstance(application)
            val remote = RemoteRepository.getInstance(apiEndpoint)

            return remote?.let { MainDataRepository.getInstance(it) } }
        }
    }
