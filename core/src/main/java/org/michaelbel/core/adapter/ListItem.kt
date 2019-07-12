package org.michaelbel.core.adapter

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ListItem {
    fun getData(): Any
    fun getViewType(): Int
    fun getId(): Long = RecyclerView.NO_ID
    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun getChangePayload(item: ListItem): Bundle? = null
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>): Boolean = false
    fun setActive(itemView: View, position: Int) {}
    fun setInactivate(itemView: View, position: Int) {}
}