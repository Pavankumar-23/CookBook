package com.example.halfway.network

import androidx.lifecycle.LiveData
import com.example.halfway.model.Facts
import com.example.halfway.model.FactsList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getFactsFromService() : Call<FactsList>
}