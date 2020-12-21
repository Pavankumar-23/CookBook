package com.example.halfway.data

import com.example.halfway.data.cache.FactsDao
import com.example.halfway.model.Facts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: FactsDao
) {

    suspend fun insertFacts(facts: Array<Facts>): LongArray {
        return dao.insertFacts(facts)
    }

    suspend fun updateRecipe(id: Int, title: String, desc: String, imageUrl: String) {
        dao.updateRecipe(id, title, desc, imageUrl)
    }

    fun readFacts(): Flow<List<Facts>> {
        return dao.getFacts()
    }
}