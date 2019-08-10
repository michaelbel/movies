package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_trailer.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TRAILER_ITEM
import org.michaelbel.data.remote.model.Video
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig.YOUTUBE_IMAGE
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

data class TrailerListItem(internal var trailer: Video): ListItem {

    interface Listener {
        fun onClick(video: Video)
        fun onLongClick(video: Video): Boolean = false
    }

    lateinit var listener: Listener

    override fun getData() = trailer

    override fun getViewType() = TRAILER_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_trailer, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.trailerName.text = trailer.name
        holder.itemView.qualityBadge.text = holder.itemView.context.getString(R.string.video_size, trailer.size.toString())

        Glide.with(holder.itemView.context).load(String.format(Locale.US, YOUTUBE_IMAGE, trailer.key))
                .thumbnail(0.1F).into(holder.itemView.image)

        holder.itemView.playerIcon.setImageDrawable(if (trailer.site == "YouTube")
            ViewUtil.getIcon(holder.itemView.context, R.drawable.ic_youtube, R.color.youtubeColor) else
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_play_circle))

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick(getData())
                }
            }
        })
        holder.itemView.setOnLongClickListener {
            if (holder.adapterPosition != NO_POSITION) {
                listener.onLongClick(getData())
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}