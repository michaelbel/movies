package org.michaelbel.moviemade.presentation.features.recommendations

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.MoviesService
import java.util.*

class RcmdRepository internal constructor(
        private val service: MoviesService
): RcmdContract.Repository {

    override fun getRcmdMovies(movieId: Int, page: Int): Observable<MoviesResponse> =
        service.getRecommendations(movieId, TMDB_API_KEY, Locale.getDefault().language, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}