package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.POWERED_ITEM
import org.michaelbel.moviemade.databinding.ListitemPoweredBinding
import java.io.Serializable

data class PoweredListItem(private val data: Data): ListItem {

    data class Data(@StringRes internal var text: Int): Serializable

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = POWERED_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemPoweredBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.binding.poweredText.setText(data.text)
    }

    private inner class ViewHolder(val binding: ListitemPoweredBinding): RecyclerView.ViewHolder(binding.root)
}