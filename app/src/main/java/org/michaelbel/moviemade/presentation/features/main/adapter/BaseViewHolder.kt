package org.michaelbel.moviemade.presentation.features.main.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    protected open val context: Context
        get() = itemView.context
}