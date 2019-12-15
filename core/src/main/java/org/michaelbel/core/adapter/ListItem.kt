package org.michaelbel.core.adapter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ListItem {
    val id: Long
    val viewType: Int

    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun getChangePayload(item: ListItem): Bundle? = null
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>): Boolean = false
    fun activate(itemView: View, position: Int) {}
    fun deactivate(itemView: View, position: Int) {}
}