package com.example.halfway.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class FactsDb : RoomDatabase(){
    abstract fun factsDao(): FactsDao

    companion object {

        @Volatile private var INSTANCE: FactsDb? = null

        fun getInstance(context: Context): FactsDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                FactsDb::class.java, "Sample.db")
                .build()
    }
}