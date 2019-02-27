package org.michaelbel.moviemade.presentation.features.trailers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import butterknife.internal.DebouncingOnClickListener
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_trailer.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.core.utils.DeviceUtil
import org.michaelbel.moviemade.core.utils.ViewUtil
import org.michaelbel.moviemade.core.utils.YOUTUBE_IMAGE
import java.util.*

class TrailersAdapter(
        private val listener: Listener
): RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>() {

    private val trailers = ArrayList<Video>()

    interface Listener {
        fun onTrailerClick(video: Video)
        fun onTrailerLongClick(video: Video): Boolean
    }

    fun getTrailers(): List<Video> {
        return trailers
    }

    fun addTrailers(results: List<Video>) {
        trailers.addAll(results)
        notifyItemRangeInserted(trailers.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_trailer, parent, false)
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
            trailer_name.text = trailer.name
            quality_badge.text = containerView.context.getString(R.string.video_size, trailer.size.toString())
            Glide.with(containerView.context)
                    .load(String.format(Locale.US, YOUTUBE_IMAGE, trailer.key))
                    .thumbnail(0.1F)
                    .into(still_image)

            if (trailer.site == "YouTube") {
                player_icon.setImageDrawable(
                        ViewUtil.getIcon(containerView.context, R.drawable.ic_youtube,
                                ContextCompat.getColor(containerView.context, R.color.youtubeColor)))
            } else {
                player_icon.setImageDrawable(
                        ViewUtil.getIcon(containerView.context, R.drawable.ic_play_circle,
                                ContextCompat.getColor(containerView.context, R.color.iconActiveColor)))
            }
        }
    }
}