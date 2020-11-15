package com.example.halfway.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.halfway.model.Facts

@Dao
interface FactsDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertFacts(recipe: Array<Facts>): LongArray

    @Query("SELECT * FROM facts_table Order by id Desc")
    fun getFacts(): LiveData<List<Facts>>

    @Query("UPDATE facts_table SET title = :title,description = :desc, imageUrl = :imageUrl WHERE id = :id")
    fun updateRecipe(id: Int, title: String, desc: String, imageUrl: String)
}





