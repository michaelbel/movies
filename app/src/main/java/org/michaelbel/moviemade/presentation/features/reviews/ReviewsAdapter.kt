package org.michaelbel.moviemade.presentation.features.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import butterknife.internal.DebouncingOnClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_review.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Review
import java.util.*

class ReviewsAdapter(
        private val reviewClickListener: Listener
): RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    interface Listener {
        fun onReviewClick(review: Review, view: View)
    }

    private val reviews = ArrayList<Review>()

    fun getReviews(): List<Review> {
        return reviews
    }

    fun setReviews(results: List<Review>) {
        reviews.addAll(results)
        notifyItemRangeInserted(reviews.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_review, parent, false)
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
            author_name.text = review.author
            review_text.text = review.content
        }
    }
}