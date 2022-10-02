package org.michaelbel.movies.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.MovieData

class MoviesPagingSource(
    private val movieInteractor: MovieInteractor,
    private val list: String,
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
            val (items, totalPages) = movieInteractor.movieList(list, nextPage)
            isLoadingFlow.emit(false)
            LoadResult.Page(
                data = items,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage == totalPages) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}