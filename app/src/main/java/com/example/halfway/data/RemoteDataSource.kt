package com.example.halfway.data

import com.example.halfway.model.FactsList
import com.example.halfway.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getFacts(): Response<FactsList> {
        return apiService.getFactsFromService()
    }
}