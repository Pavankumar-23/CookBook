package com.example.halfway.cache

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.halfway.model.Facts

@Database(entities = [Facts::class], version = 1)
abstract class FactsDb : RoomDatabase() {
    abstract fun factsDao(): FactsDao

    companion object {

        @Volatile
        private var INSTANCE: FactsDb? = null

        fun getDatabase(context: Context): FactsDb {
            try {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FactsDb::class.java,
                        "facts_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            } catch (e: Exception) {
                Log.e("FactDB",e.message)
            }
            return INSTANCE!!
        }
    }
}