package org.michaelbel.moviemade.ui.modules.reviews

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.ReviewsResponse
import org.michaelbel.moviemade.data.service.MoviesService
import org.michaelbel.moviemade.utils.LangUtil

class ReviewsRepository internal constructor(private val service: MoviesService) : ReviewsContract.Repository {

    override fun getReviews(movieId: Int): Observable<ReviewsResponse> {
        // FIXME paging
        return service.getReviews(movieId, BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
