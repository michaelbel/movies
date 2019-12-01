package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.install.model.AppUpdateType
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.Browser
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Links.ACCOUNT_MARKET
import org.michaelbel.moviemade.core.Links.ACCOUNT_WEB
import org.michaelbel.moviemade.core.Links.APP_MARKET
import org.michaelbel.moviemade.core.Links.APP_WEB
import org.michaelbel.moviemade.core.Links.EMAIL
import org.michaelbel.moviemade.core.Links.GITHUB_URL
import org.michaelbel.moviemade.core.Links.PAYPAL_ME
import org.michaelbel.moviemade.core.Links.TELEGRAM_URL
import org.michaelbel.moviemade.ktx.getViewModel
import org.michaelbel.moviemade.ktx.reObserve
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.sources.SourcesFragment

class AboutFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"
        private const val APP_UPDATE_REQUEST_CODE = 189
    }

    private val adapter = ListAdapter()
    private val viewModel: AboutModel by lazy { getViewModel<AboutModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            title = getString(R.string.about)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().finish() }
        }

        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.items.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { cell ->
                when (cell) {
                    AboutModel.Keys.Rate -> {
                        try {
                            val intent = Intent(ACTION_VIEW).apply { data = APP_MARKET.toUri() }
                            startActivity(intent)
                        } catch (e: Exception) {
                            Browser.openUrl(requireContext(), APP_WEB)
                        }
                    }
                    AboutModel.Keys.Fork -> Browser.openUrl(requireContext(), GITHUB_URL)
                    AboutModel.Keys.Libs -> {
                        requireFragmentManager().commitNow {
                            add((requireActivity() as ContainerActivity).container.id, SourcesFragment())
                            addToBackStack(tag)
                        }
                    }
                    AboutModel.Keys.Apps -> {
                        try {
                            val intent = Intent(ACTION_VIEW).apply { data = ACCOUNT_MARKET.toUri() }
                            startActivity(intent)
                        } catch (e: Exception) {
                            Browser.openUrl(requireContext(), ACCOUNT_WEB)
                        }
                    }
                    AboutModel.Keys.Feedback -> {
                        try {
                            val packageInfo = requireContext().packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0)
                            if (packageInfo != null) {
                                val telegram = Intent(ACTION_VIEW, TELEGRAM_URL.toUri())
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
                            putExtra(EXTRA_TEXT, APP_WEB)
                        }
                        startActivity(createChooser(intent, getString(R.string.share_via)))
                    }
                    AboutModel.Keys.Donate -> Browser.openUrl(requireContext(), PAYPAL_ME)
                    AboutModel.Keys.Update -> viewModel.appUpdateManager?.startUpdateFlowForResult(viewModel.appUpdateInfo?.result, AppUpdateType.IMMEDIATE, requireActivity(), APP_UPDATE_REQUEST_CODE)
                }
            }
        })
    }

    private fun feedbackEmail() {
        val intent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtras(bundleOf(EXTRA_EMAIL to EMAIL, EXTRA_SUBJECT to getString(R.string.subject), EXTRA_TEXT to ""))
        }
        startActivity(createChooser(intent, getString(R.string.feedback)))
    }
}