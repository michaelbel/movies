package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TRAILER_ITEM
import org.michaelbel.data.remote.model.Video
import org.michaelbel.data.remote.model.Video.Companion.SITE_YOUTUBE
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.YOUTUBE_IMAGE
import org.michaelbel.moviemade.databinding.ListitemTrailerBinding
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.loadImage
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

data class TrailerListItem(private val trailer: Video): ListItem {

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TRAILER_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder

        holder.binding.trailerName.text = trailer.name
        holder.binding.qualityBadge.text = holder.itemView.context.getString(R.string.video_size, trailer.size.toString())

        holder.binding.image.loadImage(String.format(Locale.US, YOUTUBE_IMAGE, trailer.key))

        holder.binding.playerIcon.setImageDrawable(if (trailer.site == SITE_YOUTUBE)
            holder.itemView.context.getIcon(R.drawable.ic_youtube, R.color.youtubeColor) else
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_play_circle))

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick(trailer)
                }
            }
        })
        holder.itemView.setOnLongClickListener {
            if (holder.bindingAdapterPosition != NO_POSITION) {
                listener.onLongClick(trailer)
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    private inner class ViewHolder(val binding: ListitemTrailerBinding): RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onClick(video: Video)
        fun onLongClick(video: Video): Boolean = false
    }
}