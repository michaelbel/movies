package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_crew.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.CREW_ITEM
import org.michaelbel.moviemade.R

data class CrewListItem(private val credits: Data): ListItem {

    data class Data(@StringRes val category: Int, val list: String?)

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = CREW_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_crew, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.itemView.crewText.text = credits.list
        holder.itemView.crewText.boldSpanText(context.getString(credits.category))
        holder.itemView.crewText.foregroundSpanText(context.getString(credits.category), R.color.textColorPrimary)
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}