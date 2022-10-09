package org.michaelbel.movies.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.MovieData

class MoviesPagingSource(
    private val movieInteractor: MovieInteractor
): PagingSource<Int, MovieData>() {

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        val anchorPosition: Int = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val page: Int = params.key ?: 1

            val (items, totalPages) = movieInteractor.movieList(
                list = MovieData.NOW_PLAYING,
                page = page
            )

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}