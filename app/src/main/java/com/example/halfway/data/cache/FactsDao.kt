package com.example.halfway.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.halfway.model.Facts
import kotlinx.coroutines.flow.Flow

@Dao
interface FactsDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertFacts(recipe: Array<Facts>): LongArray

    @Query("SELECT * FROM facts_table Order by id Asc")
    fun getFacts(): Flow<List<Facts>>

    @Query("UPDATE facts_table SET title = :title,description = :desc, imageUrl = :imageUrl WHERE id = :id")
    suspend fun updateRecipe(id: Int, title: String, desc: String, imageUrl: String)
}





