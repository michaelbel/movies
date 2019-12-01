package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_about.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.ABOUT_ITEM
import org.michaelbel.moviemade.R
import java.io.Serializable

data class AboutListItem(private val data: Data): ListItem {

    data class Data(var appName: String, var version: String): Serializable

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = ABOUT_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_about, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.appName.text = data.appName
        holder.itemView.versionText.text = data.version
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}