package org.michaelbel.moviemade.presentation.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_movie.*
import org.michaelbel.data.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

class MoviesAdapter(
        private val listener: Listener
): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    interface Listener {
        fun onMovieClick(movie: Movie)
    }

    val movies = ArrayList<Movie>()

    fun addMovies(results: List<Movie>) {
        movies.addAll(results)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_movie, parent, false)
        val holder = MoviesViewHolder(view)

        val landscape = DeviceUtil.isLandscape(parent.context) || DeviceUtil.isTablet(parent.context)
        view.layoutParams.height = if (landscape) (parent.width / 2.5F).toInt() else (parent.width / 2 * 1.5F).toInt()

        return holder
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MoviesViewHolder(override val containerView: View):
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(movie: Movie) {
            Glide.with(containerView.context)
                    .load(String.format(Locale.US, TMDB_IMAGE, "w342", movie.posterPath))
                    .thumbnail(0.1F)
                    .into(poster)

            containerView.setOnClickListener(object: DebouncingOnClickListener() {
                override fun doClick(v: View) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onMovieClick(movies[adapterPosition])
                    }
                }
            })
        }
    }
}