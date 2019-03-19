package org.michaelbel.moviemade.presentation.features.keywords

import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class KeywordsPresenter(
        private val repository: KeywordsRepository
): Presenter(), KeywordsContract.Presenter {

    private lateinit var view: KeywordsContract.View

    override fun attach(view: KeywordsContract.View) {
        this.view = view
    }

    override fun keywords(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        disposable.add(repository.keywords(movieId)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(EmptyViewMode.MODE_NO_KEYWORDS)
                        return@subscribe
                    }
                    view.content(it)
                }, { view.error(EmptyViewMode.MODE_NO_KEYWORDS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}