package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_keyword.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.KEYWORD_ITEM
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener

data class KeywordListItem(private val keyword: Keyword): ListItem {

    interface Listener {
        fun onClick(keyword: Keyword) {}
        fun onLongClick(keyword: Keyword): Boolean = false
    }

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = KEYWORD_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_keyword, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.keywordName.text = keyword.name

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick(keyword)
                }
            }
        })
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}