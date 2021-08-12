package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.ABOUT
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.common.base.BaseFragment

@AndroidEntryPoint
class SettingsFragment: BaseFragment(R.layout.fragment_lce) {

    private val viewModel: SettingsModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            title = getString(R.string.settings)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().finish() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.items.collect { listAdapter.setItems(it) }
            }
            launch {
                viewModel.click.collect {
                    when (it) {
                        SettingsModel.Keys.About -> {
                            requireActivity().startActivity<ContainerActivity> { putExtra(FRAGMENT_NAME, ABOUT) }
                        }
                    }
                }
            }
        }
    }
}