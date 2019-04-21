package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_text.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class TextListItem(internal var data: Data): ListItem {

    data class Data(
            @DrawableRes internal val icon: Int,
            @StringRes internal var text: Int,
            internal val divider: Boolean = true
    ): Serializable

    interface Listener {
        fun onClick()
    }

    lateinit var listener: Listener

    override fun getData() = data

    override fun getViewType() = ViewTypes.TEXT_ITEM

    override fun getId() = RecyclerView.NO_ID

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_text, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.iconView.setImageResource(getData().icon)
        holder.itemView.textView.setText(getData().text)
        holder.itemView.divider.visibility = if (getData().divider) VISIBLE else GONE
        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                listener.onClick()
            }
        })
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}