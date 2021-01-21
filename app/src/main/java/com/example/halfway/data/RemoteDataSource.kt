package com.example.halfway.data

import com.example.halfway.data.network.ApiService
import com.example.halfway.model.Receipe
import com.example.halfway.util.Constants
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getRecipes(
        queryMap: HashMap<String, String>
    ): Response<Receipe> =
        apiService.getFactsFromService(queryMap)


    suspend fun getRecipes(
        key: String,
        recipe_info: Boolean,
        ingredients: Boolean,
        items: Int,
        diet: String
    ): Response<Receipe> =
        apiService.getFactsFromService(key, recipe_info, ingredients, items, diet)


    suspend fun getRecipes(
        key: String,
        recipe_info: Boolean,
        ingredients: Boolean,
        items: Int,
        page: Int
    ): Receipe = apiService.getFactsFromService(key, recipe_info, ingredients, items, page)
}