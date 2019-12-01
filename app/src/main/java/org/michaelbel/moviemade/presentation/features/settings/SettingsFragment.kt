package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.getViewModel
import org.michaelbel.moviemade.ktx.reObserve
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.ABOUT
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME
import org.michaelbel.moviemade.presentation.common.base.BaseFragment

class SettingsFragment: BaseFragment(R.layout.fragment_lce) {

    private val adapter = ListAdapter()
    private val viewModel: SettingsModel by lazy { getViewModel<SettingsModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            title = getString(R.string.settings)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().finish() }
        }
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.items.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { key ->
                when (key) {
                    SettingsModel.Keys.About -> requireActivity().startActivity<ContainerActivity> { putExtra(FRAGMENT_NAME, ABOUT) }
                }
            }
        })
    }
}