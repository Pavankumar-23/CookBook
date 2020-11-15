package com.example.halfway.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.halfway.cache.FactsDao
import com.example.halfway.model.Facts
import com.example.halfway.model.FactsList
import com.example.halfway.network.RetroFitClient
import com.example.halfway.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepo(
    private val context: Context,
    val viewModelScope: CoroutineScope,
    val factsDao: FactsDao
) {

    private val TAG = "MainRepo"
    private var instance: MainRepo? = null
    var factsList: MediatorLiveData<List<Facts>> = MediatorLiveData()

    fun getInstance(): MainRepo {
        if (instance == null) {
            instance = MainRepo(context, viewModelScope, factsDao)
        }
        return instance as MainRepo
    }

    fun getFacts() {
        try {
            val facts: LiveData<List<Facts>> = loadFromDB()
            if (facts.value == null) {
                if (Util.isNetworkConnectionAvailable(context)) {
                    loadFromServer(facts)
                }
            } else {
                factsList.addSource(facts) {
                    setValue(it)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getFacts: " + e.message)
        }
    }

    private fun loadFromServer(facts: LiveData<List<Facts>>) {
        try {
            val call = RetroFitClient.getInstance().getFactsFromService()
            call.enqueue(object : Callback<FactsList> {
                override fun onResponse(call: Call<FactsList>, response: Response<FactsList>) {
                    if (response.body() != null) {
                        cacheServiceResult(response.body()!!.rows)
                        setValue(response.body()!!.rows)
                    }
                }

                override fun onFailure(call: Call<FactsList>, t: Throwable) {
                    Log.e(TAG, t.message)
                }

            })
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    private fun cacheServiceResult(list: List<Facts>) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                if (list != null) {
                    val facts: Array<Facts> = list.toTypedArray()
                    for ((index, rowid) in factsDao.insertFacts(facts).withIndex()) {
                        if (rowid == -1L) {
                            factsDao.updateRecipe(
                                facts[index].id,
                                facts[index].title,
                                facts[index].description,
                                facts[index].imageUrl
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    fun loadFromDB(): LiveData<List<Facts>> {
        return factsDao.getFacts()
    }

    private fun setValue(newValue: List<Facts>) {
        if (factsList.getValue() !== newValue) {
            factsList.setValue(newValue)
        }
    }

    fun getAsLiveData(): LiveData<List<Facts>> {
        return factsList
    }
}