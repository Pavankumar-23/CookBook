package com.example.halfway.data.network

import com.example.halfway.model.FactsList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    suspend fun getFactsFromService() : Response<FactsList>
}