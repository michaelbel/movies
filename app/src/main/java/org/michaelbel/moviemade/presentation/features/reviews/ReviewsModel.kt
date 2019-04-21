package org.michaelbel.moviemade.presentation.features.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.data.Review
import org.michaelbel.domain.ReviewsRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.errors.EmptyViewMode
import java.util.*

class ReviewsModel(val repository: ReviewsRepository): ViewModel() {

    private val disposable = CompositeDisposable()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<List<Review>>()
    var error = MutableLiveData<Int>()

    fun reviews(movieId: Int) {
        // fixme pagination
        disposable.add(repository.reviews(movieId, TMDB_API_KEY, Locale.getDefault().language, 1)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(EmptyViewMode.MODE_NO_REVIEWS)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue(EmptyViewMode.MODE_NO_REVIEWS) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}