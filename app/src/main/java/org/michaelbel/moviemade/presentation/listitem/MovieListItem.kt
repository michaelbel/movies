package org.michaelbel.moviemade.presentation.listitem

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
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
        holder as ViewHolder
        holder.binding.findViewById<AppCompatImageView>(R.id.poster).loadImage(String.format(Locale.US, TMDB_IMAGE, "w342", movie.posterPath))

        holder.binding.findViewById<ConstraintLayout>(R.id.buttonsLayout).createGradientDrawable(R.color.transparent60, topStartRadiusDimen = 4F, topEndRadiusDimen = 4F)

        holder.binding.findViewById<FrameLayout>(R.id.watchlistLayout).createGradientDrawable(topStartRadiusDimen = 4F)
        holder.binding.findViewById<FrameLayout>(R.id.watchlistLayout).setOnClickListener {}

        holder.binding.findViewById<AppCompatImageView>(R.id.watchlistIcon).setIcon(R.drawable.ic_bookmark_outline, R.color.textColorPrimary)

        holder.binding.findViewById<FrameLayout>(R.id.favoriteLayout).createGradientDrawable(topEndRadiusDimen = 4F)
        holder.binding.findViewById<FrameLayout>(R.id.favoriteLayout).setOnClickListener {}

        holder.binding.findViewById<AppCompatImageView>(R.id.favoriteIcon).setIcon(R.drawable.ic_heart_outline, R.color.textColorPrimary)

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick(movie)
                }
            }
        })
        holder.itemView.setOnLongClickListener {
            if (holder.bindingAdapterPosition != NO_POSITION) {
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
                view.isVisible = true
            }
            start()
        }
    }

    private inner class ViewHolder(val binding: View): RecyclerView.ViewHolder(binding)
}