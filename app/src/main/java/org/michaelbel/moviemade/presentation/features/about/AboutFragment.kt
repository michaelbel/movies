package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_lce.*
import kotlinx.android.synthetic.main.view_about.*
import kotlinx.android.synthetic.main.view_cell.*
import kotlinx.android.synthetic.main.view_powered.*
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Links.ACCOUNT_MARKET
import org.michaelbel.moviemade.core.Links.ACCOUNT_WEB
import org.michaelbel.moviemade.core.Links.APP_MARKET
import org.michaelbel.moviemade.core.Links.APP_WEB
import org.michaelbel.moviemade.core.Links.EMAIL
import org.michaelbel.moviemade.core.Links.GITHUB_URL
import org.michaelbel.moviemade.core.Links.PAYPAL_ME
import org.michaelbel.moviemade.core.Links.TELEGRAM_URL
import org.michaelbel.moviemade.core.customtabs.Browser
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import timber.log.Timber

class AboutFragment: BaseFragment() {

    companion object {
        private const val LIBS_FRAGMENT_TAG = "libs_fragment"
        private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"
    }

    private var rowCount: Int = 0
    private var infoRow: Int = 0
    private var forkGithubRow: Int = 0
    private var rateGooglePlay: Int = 0
    private var otherAppsRow: Int = 0
    private var libsRow: Int = 0
    private var feedbackRow: Int = 0
    private var shareFriendsRow: Int = 0
    private var donatePaypalRow: Int = 0
    private var poweredByRow: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AboutActivity).toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        (requireActivity() as AboutActivity).supportActionBar?.setTitle(R.string.about)

        rowCount = 0
        infoRow = rowCount++
        rateGooglePlay = rowCount++
        forkGithubRow = rowCount++
        libsRow = rowCount++
        otherAppsRow = rowCount++
        feedbackRow = rowCount++
        shareFriendsRow = rowCount++
        donatePaypalRow = rowCount++
        poweredByRow = rowCount++

        recyclerView.adapter = AboutAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun doAction(position: Int) {
        when (position) {
            forkGithubRow -> Browser.openUrl(requireContext(), GITHUB_URL)
            rateGooglePlay ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(APP_MARKET)
                    startActivity(intent)
                } catch (e: Exception) {
                    Browser.openUrl(requireContext(), APP_WEB)
                }
            otherAppsRow ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(ACCOUNT_MARKET)
                    startActivity(intent)
                } catch (e: Exception) {
                    Browser.openUrl(requireContext(), ACCOUNT_WEB)
                }
            libsRow ->
                requireFragmentManager()
                        .beginTransaction()
                        .replace((requireActivity() as AboutActivity).container.id, LibsFragment())
                        .addToBackStack(LIBS_FRAGMENT_TAG)
                        .commit()
            feedbackRow ->
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
                    Timber.e(e)
                }
            shareFriendsRow -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, APP_WEB)
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
            }
            donatePaypalRow -> Browser.openUrl(requireContext(), PAYPAL_ME)
        }
    }

    private inner class AboutAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                0 -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_about, parent, false)
                    HeaderVH(view)
                }
                1 -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_powered, parent, false)
                    FooterVH(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cell, parent, false)
                    AboutVH(view)
                }
            }
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            val type = getItemViewType(position)

            when (type) {
                0 -> {
                    val holder = (viewHolder as HeaderVH)
                    holder.bind(
                        getString(R.string.app_for_android, getString(R.string.app_name)),
                        getString(
                                R.string.version_build_date,
                                BuildConfig.VERSION_NAME,
                                BuildConfig.VERSION_CODE,
                                BuildConfig.VERSION_DATE
                        )
                    )
                }
                1 -> {
                    val holder = (viewHolder as FooterVH)
                    holder.bind(getString(R.string.powered_by))
                }
                else -> {
                    val holder = (viewHolder as AboutVH)
                    when (position) {
                        rateGooglePlay -> holder.bind(R.drawable.ic_google_play, R.string.rate_google_play)
                        forkGithubRow -> holder.bind(R.drawable.ic_github, R.string.fork_github)
                        libsRow -> holder.bind(R.drawable.ic_storage, R.string.open_source_libs)
                        otherAppsRow -> holder.bind(R.drawable.ic_shop, R.string.other_developer_apps)
                        feedbackRow -> holder.bind(R.drawable.ic_mail, R.string.feedback)
                        shareFriendsRow -> holder.bind(R.drawable.ic_share, R.string.share_with_friends)
                        donatePaypalRow -> holder.bind(R.drawable.ic_paypal, R.string.donate_paypal)
                    }
                }
            }
        }

        override fun getItemCount() = rowCount

        override fun getItemViewType(position: Int): Int = when (position) {
            infoRow -> 0
            poweredByRow -> 1
            else -> 2
        }

        internal inner class HeaderVH(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(app: String, version: String) {
                appName.text = app
                versionText.text = version
                containerView.setOnClickListener(object: DebouncingOnClickListener() {
                    override fun doClick(v: View) {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            doAction(adapterPosition)
                        }
                    }
                })
            }
        }

        internal inner class AboutVH(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(@DrawableRes resId: Int, @StringRes text: Int) {
                iconView.setImageResource(resId)
                textView.setText(text)
                containerView.setOnClickListener(object: DebouncingOnClickListener() {
                    override fun doClick(v: View) {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            doAction(adapterPosition)
                        }
                    }
                })
            }
        }

        internal inner class FooterVH(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(text: String) {
                poweredText.text = text
                containerView.setOnClickListener(object: DebouncingOnClickListener() {
                    override fun doClick(v: View) {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            doAction(adapterPosition)
                        }
                    }
                })
            }
        }
    }
}