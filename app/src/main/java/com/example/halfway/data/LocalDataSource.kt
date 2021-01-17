package com.example.halfway.data

import com.example.halfway.data.cache.FactsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.halfway.model.Result

class LocalDataSource @Inject constructor(
    private val dao: FactsDao
) {

    suspend fun insertFacts(facts: Array<Result>): LongArray {
        return dao.insertRecipes(facts)
    }

    fun readFacts(): Flow<List<Result>> {
        return dao.getRecipes()
    }
}