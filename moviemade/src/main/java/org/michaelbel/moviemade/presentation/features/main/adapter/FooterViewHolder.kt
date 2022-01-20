package org.michaelbel.moviemade.presentation.features.main.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import org.michaelbel.moviemade.databinding.ListitemFooterBinding

class FooterViewHolder(
    private val binding: ListitemFooterBinding,
    retry: () -> Unit
): BaseViewHolder(binding.root) {

    init {
        binding.textView.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.textView.isVisible = loadState is LoadState.Error
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}