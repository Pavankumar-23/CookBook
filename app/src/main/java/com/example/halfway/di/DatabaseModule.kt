package com.example.halfway.di

import android.content.Context
import androidx.room.Room
import com.example.halfway.data.cache.FactsDb
import com.example.halfway.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FactsDb::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(factsDb: FactsDb) = factsDb.factsDao()
}