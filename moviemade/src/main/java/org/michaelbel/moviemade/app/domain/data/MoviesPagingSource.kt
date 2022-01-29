package org.michaelbel.moviemade.app.domain.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.MovieResponse
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val api: Api,
    private val list: String,
    private val apiKey: String,
    private val language: String
): PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val response = api.movies(list, apiKey, language, position)
            val results = response.results
            val nextKey = if (results.isEmpty()) {
                null
            } else {
                position.plus(1)
            }
            LoadResult.Page(
                data = results,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position.minus(1),
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

private const val MOVIES_STARTING_PAGE_INDEX = 1