package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_movie.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes
import org.michaelbel.data.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

data class MovieListItem(internal var movie: Movie): ListItem {

    interface Listener {
        fun onClick(movie: Movie) {}
        fun onLongClick(movie: Movie): Boolean = false
    }

    lateinit var listener: Listener

    override fun getData() = movie

    override fun getViewType() = ViewTypes.MOVIE_ITEM

    override fun getId() = RecyclerView.NO_ID

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_movie, parent, false)
        val holder = ViewHolder(view)

        val landscape = DeviceUtil.isLandscape(parent.context) || DeviceUtil.isTablet(parent.context)
        view.layoutParams.height = if (landscape) (parent.width / 2.5F).toInt() else (parent.width / 2 * 1.5F).toInt()

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
                .load(String.format(Locale.US, TmdbConfig.TMDB_IMAGE, "w342", movie.posterPath))
                .thumbnail(0.1F)
                .into(holder.itemView.poster)

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(getData())
                }
            }
        })
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}