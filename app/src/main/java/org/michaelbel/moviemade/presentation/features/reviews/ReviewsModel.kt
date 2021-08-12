package org.michaelbel.moviemade.presentation.features.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.remote.model.Review
import org.michaelbel.domain.ReviewsRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_CONNECTION
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_REVIEWS
import org.michaelbel.moviemade.presentation.listitem.ReviewListItem
import java.util.ArrayList
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReviewsModel @Inject constructor(val repository: ReviewsRepository): ViewModel() {

    private var page: Int = 0
    private val itemsManager = Manager()

    var loading = MutableSharedFlow<Boolean>()
    var content = MutableSharedFlow<List<ListItem>>()
    var error = MutableSharedFlow<Int>()
    var click = MutableSharedFlow<Review>()
    var longClick = MutableSharedFlow<Review>()

    fun reviews(movieId: Long) {
        viewModelScope.launch {
            page += 1
            loading.emit(page == 1)

            try {
                val result = repository.reviews(movieId, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val reviews = result.body()?.results
                        if (reviews.isNullOrEmpty()) {
                            if (page == 1) {
                                error.emit(MODE_NO_REVIEWS)
                            }
                        } else {
                            itemsManager.updateReviews(reviews, page == 1)
                            content.emit(itemsManager.get())
                        }

                        loading.emit(false)
                    } else {
                        if (page == 1) {
                            error.emit(MODE_NO_REVIEWS)
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.emit(MODE_NO_CONNECTION)
                    loading.emit(false)
                }
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val reviews = ArrayList<ListItem>()

        fun updateReviews(items: List<Review>, update: Boolean = true) {
            if (update) {
                reviews.clear()
            }

            items.forEach {
                val reviewItem = ReviewListItem(it)
                reviewItem.listener = object: ReviewListItem.Listener {
                    override fun onClick(review: Review) {
                        viewModelScope.launch { click.emit(review) }
                    }

                    override fun onLongClick(review: Review): Boolean {
                        viewModelScope.launch { longClick.emit(review) }
                        return true
                    }
                }
                reviews.add(reviewItem)
            }
        }

        override fun getItems(): ArrayList<ListItem> = reviews
    }
}