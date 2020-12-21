package com.example.halfway.data.cache

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.halfway.model.Facts

@Database(entities = [Facts::class], version = 1, exportSchema = false)
abstract class FactsDb : RoomDatabase() {

    abstract fun factsDao(): FactsDao

}