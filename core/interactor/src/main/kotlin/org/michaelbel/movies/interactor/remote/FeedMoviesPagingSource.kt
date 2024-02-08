package org.michaelbel.movies.interactor.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository

// fixme R&D pass initial position on DetailsScreen
class FeedMoviesPagingSource(
    private val pagingKeyRepository: PagingKeyRepository,
    private val movieRepository: MovieRepository,
    private val movieList: String
): PagingSource<Int, MovieDb>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDb>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.page
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDb> {
        return try {
            val totalPages = pagingKeyRepository.totalPages(movieList)
            val pageDb = pagingKeyRepository.page(movieList)
            val page: Int = params.key ?: 1
            val data = movieRepository.moviesOnPage(movieList, page)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (page == totalPages) null else page.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}