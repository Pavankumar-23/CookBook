package com.example.halfway.viewmodel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.halfway.model.Facts
import com.example.halfway.model.FactsList
import com.example.halfway.repositories.MainRepo
import com.example.halfway.util.NetworkResult
import com.example.halfway.util.Util
import com.example.halfway.util.Util.Companion.nullToEmpty
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class MainViewModel @ViewModelInject constructor(
    private val mainRepo: MainRepo,
    application: Application
) : AndroidViewModel(application) {

    private val TAG = "MainViewModel"

    /** ROOM DATABASE*/
    val readFacts: LiveData<List<Facts>> = mainRepo.local.readFacts().asLiveData()

    private suspend fun insertFacts(facts: Array<Facts>): LongArray {
        val res = viewModelScope.async {
            mainRepo.local.insertFacts(facts)
        }
        var result = LongArray(0)
        try {
            result = res.await()
        } catch (e: Exception) {
            Log.e("insertFacts", e.message!!)
        }
        return result
    }

    private fun updateFact(
        id: Int,
        title: String,
        desc: String, imageUrl: String
    ) =
        viewModelScope.launch {
            mainRepo.local.updateRecipe(id, title, desc, imageUrl)
        }

    /** NETWORK */
    var factsResponse: MutableLiveData<NetworkResult<FactsList>> = MutableLiveData()

    fun getFactsFromServer() = viewModelScope.launch {
        getFactsSafeCall()
    }

    private suspend fun getFactsSafeCall() {
        factsResponse.value = NetworkResult.Loading()
        if (Util.isNetworkConnectionAvailable(getApplication())) {
            try {
                val response = mainRepo.remote.getFacts()
                factsResponse.value = handleServiceResponse(response)

                val factsDetail = factsResponse.value!!.data
                if (factsDetail != null) {
                    cacheServiceResult(factsDetail.rows)
                }
            } catch (e: Exception) {
                factsResponse.value = NetworkResult.Error("Facts Not Found.")
            }
        } else {
            factsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleServiceResponse(response: Response<FactsList>): NetworkResult<FactsList> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("TimeOut.")
            }
            response.message().toString().contains("402") -> {
                return NetworkResult.Error("API Key limited.")
            }
            response.body()!!.rows.isNullOrEmpty() -> {
                return NetworkResult.Error("No Facts Found.")
            }
            response.isSuccessful -> {
                val facts = response.body()
                return NetworkResult.Success(facts)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun cacheServiceResult(list: List<Facts>) {
        viewModelScope.launch {
            try {
                if (list != null) {
                    val facts: Array<Facts> = list.toTypedArray()
                    for ((index, rowid) in insertFacts(facts).withIndex()) {
                        if (rowid == -1L) {
                            updateFact(
                                facts[index].id,
                                facts[index].title,
                                nullToEmpty(facts[index].description),
                                nullToEmpty(facts[index].imageUrl)
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", e.message!!)
            }
        }
    }
}