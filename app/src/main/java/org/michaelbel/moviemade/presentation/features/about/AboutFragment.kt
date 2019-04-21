package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_container.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.customtabs.Browser
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Links.ACCOUNT_MARKET
import org.michaelbel.moviemade.core.Links.ACCOUNT_WEB
import org.michaelbel.moviemade.core.Links.APP_MARKET
import org.michaelbel.moviemade.core.Links.APP_WEB
import org.michaelbel.moviemade.core.Links.EMAIL
import org.michaelbel.moviemade.core.Links.GITHUB_URL
import org.michaelbel.moviemade.core.Links.PAYPAL_ME
import org.michaelbel.moviemade.core.Links.TELEGRAM_URL
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.sources.SourcesFragment

class AboutFragment: BaseFragment() {

    companion object {
        private const val FRAGMENT_TAG = "fragment_libs"
        private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"

        internal fun newInstance() = AboutFragment()
    }

    private val adapter = ListAdapter()
    private lateinit var viewModel: AboutModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(requireActivity()).get(AboutModel::class.java)
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.about)
        toolbar.navigationIcon = ViewUtil.getIcon(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.items.observe(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { cell ->
                when (cell) {
                    "rate" ->
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = APP_MARKET.toUri()
                            startActivity(intent)
                        } catch (e: Exception) { Browser.openUrl(requireContext(), APP_WEB) }
                    "fork" -> Browser.openUrl(requireContext(), GITHUB_URL)
                    "libs" ->
                        requireFragmentManager().transaction {
                            add((requireActivity() as ContainerActivity).container.id, SourcesFragment.newInstance())
                            addToBackStack(FRAGMENT_TAG)
                        }
                    "apps" ->
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = ACCOUNT_MARKET.toUri()
                            startActivity(intent)
                        } catch (e: Exception) { Browser.openUrl(requireContext(), ACCOUNT_WEB) }

                    "feedback" ->
                        try {
                            val packageInfo = requireContext().packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0)
                            if (packageInfo != null) {
                                val telegram = Intent(Intent.ACTION_VIEW, TELEGRAM_URL.toUri())
                                startActivity(telegram)
                            } else {
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.type = "text/plain"
                                intent.putExtra(Intent.EXTRA_EMAIL, EMAIL)
                                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
                                intent.putExtra(Intent.EXTRA_TEXT, "")
                                startActivity(Intent.createChooser(intent, getString(R.string.feedback)))
                            }
                        } catch (e: PackageManager.NameNotFoundException) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_EMAIL, EMAIL)
                            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
                            intent.putExtra(Intent.EXTRA_TEXT, "")
                            startActivity(Intent.createChooser(intent, getString(R.string.feedback)))
                        }
                    "share" -> {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, APP_WEB)
                        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
                    }
                    "donate" -> Browser.openUrl(requireContext(), PAYPAL_ME)
                    else -> null
                }
            }
        })
    }
}