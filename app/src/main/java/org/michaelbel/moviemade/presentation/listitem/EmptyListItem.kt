package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.EMPTY_ITEM
import org.michaelbel.moviemade.databinding.ListitemEmptyBinding
import org.michaelbel.moviemade.ktx.toDp
import java.io.Serializable

data class EmptyListItem(private val data: Data): ListItem {

    data class Data(var height: Float): Serializable

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = EMPTY_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val context = holder.itemView.context

        holder.binding.emptyView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, data.height.toDp(context))
    }

    private inner class ViewHolder(val binding: ListitemEmptyBinding): RecyclerView.ViewHolder(binding.root)
}