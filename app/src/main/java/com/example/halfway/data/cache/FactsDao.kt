package com.example.halfway.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.halfway.model.Result

@Dao
interface FactsDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertRecipes(recipe: Array<Result>): LongArray

    @Query("SELECT * FROM recipe_info Order by id Asc")
    fun getRecipes(): Flow<List<Result>>

    @Query("SELECT * FROM recipe_info WHERE id = :id")
    fun getRecipe(id: Int): Flow<Result>
}





