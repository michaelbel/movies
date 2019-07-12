package org.michaelbel.moviemade.presentation.features.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.Review
import org.michaelbel.domain.ReviewsRepository
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_REVIEWS
import org.michaelbel.moviemade.presentation.listitem.ReviewListItem
import java.util.*

class ReviewsModel(val repository: ReviewsRepository): ViewModel() {

    private var page: Int = 0
    private val itemsManager = Manager()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<ArrayList<ListItem>>()
    var error = MutableLiveData<LiveDataEvent<Int>>()
    var click = MutableLiveData<LiveDataEvent<Review>>()
    var longClick = MutableLiveData<LiveDataEvent<Review>>()

    fun reviews(movieId: Int) {
        page += 1
        loading.postValue(page == 1)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.reviews(movieId, TMDB_API_KEY, Locale.getDefault().language, page)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val reviews = result.body()?.results
                        if (reviews.isNullOrEmpty()) {
                            error.postValue(LiveDataEvent(MODE_NO_REVIEWS))
                        } else {
                            itemsManager.updateReviews(reviews, page == 1)
                            content.postValue(itemsManager.get())
                        }

                        loading.postValue(false)
                    } else {
                        if (page == 1) {
                            error.postValue(LiveDataEvent(MODE_NO_REVIEWS))
                        }
                    }
                }
            } catch (e: Throwable) {
                if (page == 1) {
                    error.postValue(LiveDataEvent(EmptyState.MODE_NO_CONNECTION))
                    loading.postValue(false)
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
                        click.postValue(LiveDataEvent(review))
                    }

                    override fun onLongClick(review: Review): Boolean {
                        longClick.postValue(LiveDataEvent(review))
                        return true
                    }
                }
                reviews.add(reviewItem)
            }
        }

        override fun getItems(): ArrayList<ListItem> {
            return reviews
        }
    }
}