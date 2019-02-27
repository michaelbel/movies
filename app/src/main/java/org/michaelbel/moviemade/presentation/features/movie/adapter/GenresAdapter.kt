package org.michaelbel.moviemade.presentation.features.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.chip_genre.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Genre
import java.util.*

class GenresAdapter: RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private val genres = ArrayList<Genre>()

    fun setGenres(results: List<Genre>) {
        genres.addAll(results)
        notifyItemRangeInserted(genres.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_genre, parent, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount() = genres.size

    inner class GenresViewHolder(override val containerView: View):
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(genre: Genre) {
            chipName.text = genre.name
        }
    }
}