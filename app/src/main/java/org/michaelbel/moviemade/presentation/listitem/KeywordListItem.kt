package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.KEYWORD_ITEM
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.moviemade.databinding.ListitemKeywordBinding
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
        return ViewHolder(ListitemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.keywordName.text = keyword.name

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick(keyword)
                }
            }
        })
    }

    private inner class ViewHolder(val binding: ListitemKeywordBinding): RecyclerView.ViewHolder(binding.root)
}