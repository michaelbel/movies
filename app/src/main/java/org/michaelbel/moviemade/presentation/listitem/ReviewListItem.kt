package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_review.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.REVIEW_ITEM
import org.michaelbel.data.remote.model.Review
import org.michaelbel.moviemade.R
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
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_review, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.authorName.text = review.author
        holder.itemView.reviewText.text = review.content

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick(review)
                }
            }
        })
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}