package com.pyramitec.secretfriend.infra.webservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SecretFriendAPI {

    private const val API_BASE_URL = "https://business-controll-backend.herokuapp.com"

    private val okkHttpClient: OkHttpClient by lazy {
        val client = OkHttpClient.Builder()
        client.addInterceptor(provideLogginInterceptor())
        client.build()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okkHttpClient)
        .build()

    val secretFriendService: SecretFriendService by lazy {
        retrofit.create(SecretFriendService::class.java)
    }

    private fun provideLogginInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }
}