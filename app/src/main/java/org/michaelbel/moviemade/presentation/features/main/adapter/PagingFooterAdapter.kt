package org.michaelbel.moviemade.presentation.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import org.michaelbel.moviemade.databinding.ListitemFooterBinding

class PagingFooterAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<FooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder(
            ListitemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}