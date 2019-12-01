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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_text.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.TEXT_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.gone
import org.michaelbel.moviemade.ktx.toDp
import org.michaelbel.moviemade.ktx.visible
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class TextListItem(private val data: Data): ListItem {

    data class Data(
            @DrawableRes val icon: Int = 0,
            @StringRes var text: Int = 0,
            val divider: Boolean = true,
            val medium: Boolean = true
    ): Serializable

    interface Listener {
        fun onClick() {}
    }

    lateinit var listener: Listener

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = TEXT_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_text, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context

        // Icon.
        if (data.icon != 0) {
            holder.itemView.iconView.visible()
            holder.itemView.iconView.setImageResource(data.icon)
        } else {
            holder.itemView.iconView.gone()
        }

        // Text.
        holder.itemView.textView.setText(data.text)
        holder.itemView.textView.typeface = if (data.medium) Typeface.create("sans-serif-medium", NORMAL) else DEFAULT

        // Divider.
        holder.itemView.divider.visibility = if (data.divider) VISIBLE else GONE

        val params: ConstraintLayout.LayoutParams = holder.itemView.divider.layoutParams as ConstraintLayout.LayoutParams
        params.marginStart = if (data.icon == 0) 16F.toDp(context) else 56F.toDp(context)
        holder.itemView.divider.layoutParams = params

        // Click.
        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener.onClick()
                }
            }
        })
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}