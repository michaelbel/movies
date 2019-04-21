package org.michaelbel.moviemade.presentation.features.keywords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.data.Keyword
import org.michaelbel.domain.KeywordsRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.errors.EmptyViewMode

class KeywordsModel(val repository: KeywordsRepository): ViewModel() {

    private val disposable = CompositeDisposable()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<List<Keyword>>()
    var error = MutableLiveData<Int>()

    fun keywords(movieId: Int) {
        disposable.add(repository.keywords(movieId, TMDB_API_KEY)
                .doOnSubscribe { loading.postValue(true) }
                .doOnTerminate { loading.postValue(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        error.postValue(EmptyViewMode.MODE_NO_KEYWORDS)
                        return@subscribe
                    }
                    content.postValue(it)
                }, { error.postValue(EmptyViewMode.MODE_NO_KEYWORDS) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}