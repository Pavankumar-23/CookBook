package com.example.halfway.network

import com.example.halfway.util.Constants
import com.example.halfway.util.Constants.Companion.BASE_URL
import com.example.halfway.util.Constants.Companion.CONNECTION_TIMEOUT
import com.example.halfway.util.Constants.Companion.READ_TIMEOUT
import com.example.halfway.util.Constants.Companion.WRITE_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroFitClient {
    companion object {
        private var instance: ApiService? = null

        fun getInstance(): ApiService {
            return instance ?: synchronized(this) {
                instance ?: buildService().also { instance = it }
            }
        }

        private fun buildService(): ApiService {
            return builderConnection().create(ApiService::class.java)
        }

        private fun builderConnection(): Retrofit {
            val okHttpClient = OkHttpClient.Builder() // establish connection to server
                .connectTimeout(
                    CONNECTION_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                ) // time between each byte read from the server
                .readTimeout(
                    READ_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                ) // time between each byte sent to server
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}