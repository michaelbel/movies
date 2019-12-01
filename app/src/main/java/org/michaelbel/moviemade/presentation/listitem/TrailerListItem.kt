package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_trailer.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TRAILER_ITEM
import org.michaelbel.data.remote.model.Video
import org.michaelbel.data.remote.model.Video.Companion.SITE_YOUTUBE
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.YOUTUBE_IMAGE
import org.michaelbel.moviemade.ktx.getIcon
import org.michaelbel.moviemade.ktx.loadImage
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

data class TrailerListItem(private val trailer: Video): ListItem {

    interface Listener {
        fun onClick(video: Video)
        fun onLongClick(video: Video): Boolean = false
    }

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TRAILER_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_trailer, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.trailerName.text = trailer.name
        holder.itemView.qualityBadge.text = holder.itemView.context.getString(R.string.video_size, trailer.size.toString())

        holder.itemView.image.loadImage(String.format(Locale.US, YOUTUBE_IMAGE, trailer.key))

        holder.itemView.playerIcon.setImageDrawable(if (trailer.site == SITE_YOUTUBE)
            holder.itemView.context.getIcon(R.drawable.ic_youtube, R.color.youtubeColor) else
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_play_circle))

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick(trailer)
                }
            }
        })
        holder.itemView.setOnLongClickListener {
            if (holder.adapterPosition != NO_POSITION) {
                listener.onLongClick(trailer)
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}