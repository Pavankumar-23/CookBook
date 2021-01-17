package com.example.halfway.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.halfway.model.Result
import com.example.halfway.util.Converters

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FactsDb : RoomDatabase() {

    abstract fun factsDao(): FactsDao

}