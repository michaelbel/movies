package org.michaelbel.moviemade.ui.modules.recommendations

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.data.service.MoviesService
import org.michaelbel.moviemade.utils.LangUtil

class RcmdRepository internal constructor(private val service: MoviesService) : RcmdContract.Repository {

    override fun getRcmdMovies(movieId: Int, page: Int): Observable<MoviesResponse> {
        return service.getRecommendations(movieId, BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}