package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_empty.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.EMPTY_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.toDp
import java.io.Serializable

data class EmptyListItem(private val data: Data): ListItem {

    data class Data(var height: Float): Serializable

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = EMPTY_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_empty, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.itemView.emptyView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, data.height.toDp(context))
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}