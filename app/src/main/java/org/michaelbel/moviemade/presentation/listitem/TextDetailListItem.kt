package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TEXT_DETAIL_ITEM
import org.michaelbel.moviemade.databinding.ListitemTextInfoBinding
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class TextDetailListItem(private val info: Data): ListItem {

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TEXT_DETAIL_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemTextInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.text.text = info.text
        holder.binding.summary.text = info.info
        holder.binding.switchWidget.visibility = if (info.checked != null) VISIBLE else GONE
        holder.binding.switchWidget.isChecked = info.checked == true
        holder.binding.divider.visibility = if (info.divider) VISIBLE else GONE

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick()

                    val newChecked: Boolean = info.checked != info.checked
                    listener.onChecked(newChecked)
                    holder.binding.switchWidget.isChecked = newChecked
                }
            }
        })
    }

    private inner class ViewHolder(val binding: ListitemTextInfoBinding): RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onClick() {}
        fun onChecked(checked: Boolean) {}
    }

    data class Data(
        var text: String? = null,
        var info: String? = null,
        var checked: Boolean? = null,
        val divider: Boolean = true
    ): Serializable
}