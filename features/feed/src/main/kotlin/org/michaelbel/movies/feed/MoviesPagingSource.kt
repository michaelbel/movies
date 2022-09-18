package org.michaelbel.movies.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import org.michaelbel.movies.core.Api
import org.michaelbel.movies.core.entities.MovieData
import org.michaelbel.movies.core.mappers.MovieMapper
import org.michaelbel.movies.core.model.MovieResponse
import org.michaelbel.movies.core.model.Result

class MoviesPagingSource(
    private val api: Api,
    private val moviesMapper: MovieMapper,
    private val list: String,
    private val apiKey: String,
    private val language: String,
    private val isLoadingFlow: MutableStateFlow<Boolean>
): PagingSource<Int, MovieData>() {

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val nextPage = params.key ?: 1
            isLoadingFlow.emit(true)
            val result: Result<MovieResponse> = api.movies(list, apiKey, language, nextPage)
            isLoadingFlow.emit(false)
            LoadResult.Page(
                data = moviesMapper.mapToMovieDataList(result.results),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage == result.totalPages) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}