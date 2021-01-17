package com.example.halfway.repositories


import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.halfway.data.LocalDataSource
import com.example.halfway.data.RecipePagingSource
import com.example.halfway.data.RemoteDataSource
import com.example.halfway.model.Result
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class MainRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource

    fun getRecipes(query: String): LiveData<PagingData<Result>> {
        val data = Pager(
            PagingConfig(
                pageSize = 10
            )
        ) {
            RecipePagingSource(remoteDataSource, query)
        }.liveData
        return data
    }
}