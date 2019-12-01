package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_powered.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.POWERED_ITEM
import org.michaelbel.moviemade.R
import java.io.Serializable

data class PoweredListItem(private val data: Data): ListItem {

    data class Data(@StringRes internal var text: Int): Serializable

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = POWERED_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_powered, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.poweredText.setText(data.text)
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}