package com.example.halfway.data

import androidx.paging.PagingSource
import com.example.halfway.model.Result
import com.example.halfway.util.Constants.Companion.START_PAGE_INDEX
import com.example.halfway.util.Constants.Companion.api_Key

class RecipePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position: Int = params.key ?: START_PAGE_INDEX
        var data: LoadResult<Int, Result>?
        try {
            val response = remoteDataSource.getRecipes(
                api_Key,
                true,
                true,
                params.loadSize,
                position
            )
            val result = response.results

            data = LoadResult.Page(
                data = result,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return data
    }

}