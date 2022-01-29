package org.michaelbel.moviemade.ui.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.MovieResponse
import org.michaelbel.moviemade.app.data.model.Result

class MoviesPagingSource(
    private val api: Api,
    private val list: String,
    private val apiKey: String,
    private val language: String,
    private val isLoadingFlow: MutableStateFlow<Boolean>
): PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val nextPage = params.key ?: 1
            isLoadingFlow.emit(true)
            val result: Result<MovieResponse> = api.movies(list, apiKey, language, nextPage)
            isLoadingFlow.emit(false)
            LoadResult.Page(
                data = result.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage == result.totalPages) null else nextPage + 1
            )
        } catch (e: Exception) {
            Log.e("2580", "Exception $e")
            LoadResult.Error(e)
        }
    }
}