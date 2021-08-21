package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.GENRE_ITEM
import org.michaelbel.data.remote.model.Genre
import org.michaelbel.moviemade.databinding.ListitemGenreBinding

data class GenreListItem(private val genre: Genre): ListItem {

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = GENRE_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.chipName.text = genre.name
    }

    private inner class ViewHolder(val binding: ListitemGenreBinding): RecyclerView.ViewHolder(binding.root)
}