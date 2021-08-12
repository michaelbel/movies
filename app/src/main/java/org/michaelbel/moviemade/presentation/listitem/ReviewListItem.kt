package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.REVIEW_ITEM
import org.michaelbel.data.remote.model.Review
import org.michaelbel.moviemade.databinding.ListitemReviewBinding
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener

data class ReviewListItem(private val review: Review): ListItem {

    interface Listener {
        fun onClick(review: Review) {}
        fun onLongClick(review: Review): Boolean = false
    }

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = REVIEW_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.authorName.text = review.author
        holder.binding.reviewText.text = review.content

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick(review)
                }
            }
        })
    }

    private inner class ViewHolder(val binding: ListitemReviewBinding): RecyclerView.ViewHolder(binding.root)
}