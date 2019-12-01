package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_text_info.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TEXT_DETAIL_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class TextDetailListItem(private val info: Data): ListItem {

    data class Data(
            var text: String? = null,
            var info: String? = null,
            var checked: Boolean? = null,
            val divider: Boolean = true
    ): Serializable

    interface Listener {
        fun onClick() {}
        fun onChecked(checked: Boolean) {}
    }

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TEXT_DETAIL_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_text_info, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text.text = info.text
        holder.itemView.summary.text = info.info
        holder.itemView.switchWidget.visibility = if (info.checked != null) VISIBLE else GONE
        holder.itemView.switchWidget.isChecked = info.checked == true
        holder.itemView.divider.visibility = if (info.divider) VISIBLE else GONE

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick()

                    val newChecked: Boolean = info.checked != info.checked
                    listener.onChecked(newChecked)
                    holder.itemView.switchWidget.isChecked = newChecked
                }
            }
        })
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}