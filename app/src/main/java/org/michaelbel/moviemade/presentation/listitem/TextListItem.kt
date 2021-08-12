package org.michaelbel.moviemade.presentation.listitem

import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT
import android.graphics.Typeface.NORMAL
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TEXT_ITEM
import org.michaelbel.moviemade.databinding.ListitemTextBinding
import org.michaelbel.moviemade.ktx.toDp
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class TextListItem(private val data: Data): ListItem {

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TEXT_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val context = holder.itemView.context

        // Icon.
        if (data.icon != 0) {
            holder.binding.iconView.isVisible = true
            holder.binding.iconView.setImageResource(data.icon)
        } else {
            holder.binding.iconView.isGone = true
        }

        // Text.
        holder.binding.textView.setText(data.text)
        holder.binding.textView.typeface = if (data.medium) Typeface.create("sans-serif-medium", NORMAL) else DEFAULT

        // Divider.
        holder.binding.divider.visibility = if (data.divider) VISIBLE else GONE

        val params: ConstraintLayout.LayoutParams = holder.binding.divider.layoutParams as ConstraintLayout.LayoutParams
        params.marginStart = if (data.icon == 0) 16F.toDp(context) else 56F.toDp(context)
        holder.binding.divider.layoutParams = params

        // Click.
        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.bindingAdapterPosition != NO_POSITION) {
                    listener.onClick()
                }
            }
        })
    }

    private inner class ViewHolder(val binding: ListitemTextBinding): RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onClick() {}
    }

    data class Data(
        @DrawableRes val icon: Int = 0,
        @StringRes var text: Int = 0,
        val divider: Boolean = true,
        val medium: Boolean = true
    ): Serializable
}