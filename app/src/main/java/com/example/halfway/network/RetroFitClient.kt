package com.example.halfway.network

import com.example.halfway.util.Constants
import com.example.halfway.util.Constants.Companion.CONNECTION_TIMEOUT
import com.example.halfway.util.Constants.Companion.READ_TIMEOUT
import com.example.halfway.util.Constants.Companion.WRITE_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroFitClient {
    private val client = OkHttpClient.Builder() // establish connection to server
        .connectTimeout(CONNECTION_TIMEOUT.toLong(),TimeUnit.SECONDS) // time between each byte read from the server
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS) // time between each byte sent to server
        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()


    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getApiService(): ApiService? {
        return apiService
    }
}