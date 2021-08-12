package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.CREW_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.ListitemCrewBinding

data class CrewListItem(private val credits: Data): ListItem {

    data class Data(@StringRes val category: Int, val list: String?)

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = CREW_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemCrewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val context = holder.itemView.context

        holder.binding.crewText.text = credits.list
        holder.binding.crewText.boldSpanText(context.getString(credits.category))
        holder.binding.crewText.foregroundSpanText(context.getString(credits.category), R.color.textColorPrimary)
    }

    private inner class ViewHolder(val binding: ListitemCrewBinding): RecyclerView.ViewHolder(binding.root)
}