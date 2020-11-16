package com.example.halfway.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.halfway.cache.FactsDb
import com.example.halfway.model.Facts
import com.example.halfway.repositories.MainRepo
import com.example.halfway.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MainViewModel"
    private val context: Application

    private val factsDao = FactsDb.getDatabase(application).factsDao()
    val mainRepo: MainRepo by lazy {
        MainRepo(
            application.applicationContext,
            viewModelScope,
            factsDao
        ).getInstance()
    }

    lateinit var factsList: LiveData<List<Facts>>

    init {
        getFactsFromServer()
        context = application
    }

    fun getFactsFromServer() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                mainRepo.getFacts()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    fun getFacts(): LiveData<List<Facts>>? {

        factsList = mainRepo.getAsLiveData()
        return factsList
    }
}