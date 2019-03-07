package org.michaelbel.moviemade.presentation.features.trailers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trailer.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.core.DeviceUtil
import org.michaelbel.moviemade.core.TmdbConfig.YOUTUBE_IMAGE
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

class TrailersAdapter(
        private val listener: Listener
): RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>() {

    private val trailers = ArrayList<Video>()

    interface Listener {
        fun onTrailerClick(video: Video)
        fun onTrailerLongClick(video: Video): Boolean
    }

    fun addTrailers(results: List<Video>) {
        trailers.addAll(results)
        notifyItemRangeInserted(trailers.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        val holder = TrailersViewHolder(view)
        view.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onTrailerClick(trailers[pos])
                }
            }
        })
        view.setOnLongClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.onTrailerLongClick(trailers[pos])
                return@setOnLongClickListener true
            }
            false
        }

        if (DeviceUtil.isLandscape(parent.context) || DeviceUtil.isTablet(parent.context)) {
            view.layoutParams.height = (parent.width / 3.5).toInt()
        } else {
            view.layoutParams.height = parent.width / 2
        }

        return holder
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        holder.bind(trailers[position])
    }

    override fun getItemCount() = trailers.size

    inner class TrailersViewHolder(override val containerView: View):
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(trailer: Video) {
            trailerName.text = trailer.name
            qualityBadge.text = containerView.context.getString(R.string.video_size, trailer.size.toString())
            Glide.with(containerView.context)
                    .load(String.format(Locale.US, YOUTUBE_IMAGE, trailer.key))
                    .thumbnail(0.1F)
                    .into(image)

            if (trailer.site == "YouTube") {
                playerIcon.setImageDrawable(
                        ViewUtil.getIcon(containerView.context, R.drawable.ic_youtube,
                                ContextCompat.getColor(containerView.context, R.color.youtubeColor)))
            } else {
                playerIcon.setImageDrawable(
                        ViewUtil.getIcon(containerView.context, R.drawable.ic_play_circle,
                                ContextCompat.getColor(containerView.context, R.color.iconActiveColor)))
            }
        }
    }
}