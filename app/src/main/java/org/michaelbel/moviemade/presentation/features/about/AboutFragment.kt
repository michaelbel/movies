package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_VIEW
import android.content.Intent.EXTRA_EMAIL
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.createChooser
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.play.core.install.model.AppUpdateType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.Browser
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Links
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.sources.SourcesFragment

@AndroidEntryPoint
class AboutFragment: BaseFragment(R.layout.fragment_lce) {

    private val viewModel: AboutModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            title = getString(R.string.about)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().finish() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        launchAndRepeatWithViewLifecycle {
            launch { viewModel.items.collect { listAdapter.setItems(it) } }
            launch {
                viewModel.click.collect {
                    when (it) {
                        AboutModel.Keys.Rate -> {
                            try {
                                val intent = Intent(ACTION_VIEW).apply { data = Links.APP_MARKET.toUri() }
                                startActivity(intent)
                            } catch (e: Exception) {
                                Browser.openUrl(requireContext(), Links.APP_WEB)
                            }
                        }
                        AboutModel.Keys.Fork -> Browser.openUrl(requireContext(), Links.GITHUB_URL)
                        AboutModel.Keys.Libs -> {
                            parentFragmentManager.commitNow {
                                add((requireActivity() as ContainerActivity).containerId, SourcesFragment())
                                addToBackStack(tag)
                            }
                        }
                        AboutModel.Keys.Apps -> {
                            try {
                                val intent = Intent(ACTION_VIEW).apply { data = Links.ACCOUNT_MARKET.toUri() }
                                startActivity(intent)
                            } catch (e: Exception) {
                                Browser.openUrl(requireContext(), Links.ACCOUNT_WEB)
                            }
                        }
                        AboutModel.Keys.Feedback -> {
                            try {
                                val packageInfo = requireContext().packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0)
                                if (packageInfo != null) {
                                    val telegram = Intent(ACTION_VIEW, Links.TELEGRAM_URL.toUri())
                                    startActivity(telegram)
                                } else {
                                    feedbackEmail()
                                }
                            } catch (e: PackageManager.NameNotFoundException) {
                                feedbackEmail()
                            }
                        }
                        AboutModel.Keys.Share -> {
                            val intent = Intent(ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(EXTRA_TEXT, Links.APP_WEB)
                            }
                            startActivity(createChooser(intent, getString(R.string.share_via)))
                        }
                        AboutModel.Keys.Donate -> Browser.openUrl(requireContext(), Links.PAYPAL_ME)
                        AboutModel.Keys.Update -> {
                            viewModel.appUpdateManager?.startUpdateFlowForResult(
                                viewModel.appUpdateInfo?.result!!,
                                AppUpdateType.IMMEDIATE,
                                requireActivity(),
                                APP_UPDATE_REQUEST_CODE
                            )
                        }
                    }
                }
            }
        }
    }

    private fun feedbackEmail() {
        val intent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtras(bundleOf(EXTRA_EMAIL to Links.EMAIL, EXTRA_SUBJECT to getString(R.string.subject), EXTRA_TEXT to ""))
        }
        startActivity(createChooser(intent, getString(R.string.feedback)))
    }

    private companion object {
        private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"
        private const val APP_UPDATE_REQUEST_CODE = 189
    }
}