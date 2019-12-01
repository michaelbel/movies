package org.michaelbel.moviemade.presentation.listitem

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnStart
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_movie.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.MOVIE_ITEM
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.TMDB_IMAGE
import org.michaelbel.moviemade.ktx.*
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

data class MovieListItem(private val movie: Movie): ListItem {

    interface Listener {
        fun onClick(movie: Movie) {}
        fun onLongClick(movie: Movie): Boolean = false
    }

    lateinit var listener: Listener

    private var animator: ObjectAnimator? = null

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = MOVIE_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_movie, parent, false)
        val landscape = parent.context.isLandscape || parent.context.isTablet
        view.layoutParams.height = if (landscape) (parent.width / 2.5F).toInt() else (parent.width / 2 * 1.5F).toInt()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.poster.loadImage(String.format(Locale.US, TMDB_IMAGE, "w342", movie.posterPath))

        holder.itemView.buttonsLayout.createGradientDrawable(R.color.transparent60, topStartRadiusDimen = 4F, topEndRadiusDimen = 4F)

        holder.itemView.watchlistLayout.createGradientDrawable(topStartRadiusDimen = 4F)
        holder.itemView.watchlistLayout.setOnClickListener {
            Log.e("1488", "watchlistLayout")
        }

        holder.itemView.watchlistIcon.setIcon(R.drawable.ic_bookmark_outline, R.color.textColorPrimary)

        holder.itemView.favoriteLayout.createGradientDrawable(topEndRadiusDimen = 4F)
        holder.itemView.favoriteLayout.setOnClickListener {
            Log.e("1488", "favoriteLayout")
        }

        holder.itemView.favoriteIcon.setIcon(R.drawable.ic_heart_outline, R.color.textColorPrimary)

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick(movie)
                }
            }
        })
        holder.itemView.setOnLongClickListener {
            if (holder.adapterPosition != NO_POSITION) {
                //animateLayout(holder.itemView.buttonsLayout)
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    private fun animateLayout(view: ConstraintLayout) {
        animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, 56F.toDp(view.context).toFloat(), 0F).apply {
            duration = 150L
            doOnStart {
                view.translationY = 56F.toDp(view.context).toFloat()
                view.visible()
            }
            start()
        }
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}