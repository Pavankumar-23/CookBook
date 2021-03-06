package com.example.halfway.viewmodel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.halfway.model.Receipe
import com.example.halfway.model.RecipeCategory
import com.example.halfway.repositories.MainRepo
import com.example.halfway.util.NetworkResult
import com.example.halfway.util.Util
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import com.example.halfway.model.Result
import com.example.halfway.util.Constants.Companion.API_KEY

class MainViewModel @ViewModelInject constructor(
    private val mainRepo: MainRepo,
    application: Application
) : AndroidViewModel(application) {

    private val tag = "MainViewModel"

    /** ROOM DATABASE*/
    val readRecipes: LiveData<List<Result>> = mainRepo.local.readFacts().asLiveData()

    private suspend fun insertFacts(facts: Array<Result>): LongArray {
        var result = LongArray(0)

        try {
            val res = viewModelScope.async {
                mainRepo.local.insertFacts(facts)
            }
            try {
                result = res.await()
            } catch (e: Exception) {
                Log.e("insertFacts", e.message!!)
            }
        } catch (e: Exception) {
            Log.e("tag", e.toString())
        }
        Log.d(tag, result.contentToString())
        return result
    }

    /** NETWORK */
    var factsResponse: MutableLiveData<NetworkResult<Receipe>> = MutableLiveData()
//    var factsResponse: MutableLiveData<PagingData<Result>> = MutableLiveData()

    fun getFactsFromServer() = viewModelScope.launch {
        getFactsSafeCall()
    }

    private suspend fun getFactsSafeCall() {

        /*factsResponse =
            mainRepo.getRecipes("") as MutableLiveData<PagingData<Result>>*/

        factsResponse.value = NetworkResult.Loading()
        if (Util.isNetworkConnectionAvailable(getApplication())) {
            try {
                val response = mainRepo.remote.getRecipes(
                    API_KEY, true, true, 100, "vegetarian"
                )
                factsResponse.value = handleServiceResponse(response)
                factsResponse.value.let {
                    if (it?.data != null) cacheServiceResult(it.data.results)
                }
            } catch (e: Exception) {
                factsResponse.value = NetworkResult.Error("Recipes Not Found.")
            }
        } else {
            factsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleServiceResponse(response: Response<Receipe>): NetworkResult<Receipe> = when {
        response.message().toString().contains("timeout") -> {
            NetworkResult.Error("TimeOut.")
        }
        response.message().toString().contains("402") -> {
            NetworkResult.Error("API Key limited.")
        }
        response.body()!!.results.isNullOrEmpty() -> {
            NetworkResult.Error("No Recipes Found.")
        }
        response.isSuccessful -> {
            val recipes = response.body()
            NetworkResult.Success(recipes)
        }
        else -> {
            NetworkResult.Error(response.message())
        }
    }

    private fun cacheServiceResult(list: List<Result>) {
        viewModelScope.launch {
            try {
                val recipes: Array<Result> = list.toTypedArray()
                for ((index, rowid) in insertFacts(recipes).withIndex()) {
                    Log.d(tag, "$index cacheServiceResult: $rowid")
                }
            } catch (e: Exception) {
                Log.e(tag, e.message!!)
            }
        }
    }

    fun readCategoryData(): List<RecipeCategory> {
        return listOf<RecipeCategory>(
            RecipeCategory("Indian", "indian"),
            RecipeCategory("Mexican", "mexican"),
            RecipeCategory("Thai", "thai"),
            RecipeCategory("Indian", "french"),
            RecipeCategory("Chinese", "chinese"),
            RecipeCategory("German", "german"),
            RecipeCategory("Italian", "italian")
        )
    }

}
