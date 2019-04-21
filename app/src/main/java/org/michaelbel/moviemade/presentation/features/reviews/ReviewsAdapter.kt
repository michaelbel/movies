package org.michaelbel.moviemade.presentation.features.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_review.*
import org.michaelbel.data.Review
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

class ReviewsAdapter(
        private val reviewClickListener: Listener
): RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    interface Listener {
        fun onReviewClick(review: Review, view: View)
    }

    private val reviews = ArrayList<Review>()

    fun setReviews(results: List<Review>) {
        reviews.addAll(results)
        notifyItemRangeInserted(reviews.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        val holder = ReviewsViewHolder(view)
        view.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    reviewClickListener.onReviewClick(reviews[pos], v)
                }
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size

    inner class ReviewsViewHolder(override val containerView: View):
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(review: Review) {
            authorName.text = review.author
            reviewText.text = review.content
        }
    }
}