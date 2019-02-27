package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.*
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener

@Deprecated("")
class AboutFragment: BaseFragment() {

    companion object {
        private const val LIBS_FRAGMENT_TAG = "libsFragment"
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
        inflater.inflate(R.layout.fragment_recycler, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AboutActivity).getToolbar().setNavigationOnClickListener { requireActivity().finish() }
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
            rateGooglePlay -> try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(APP_MARKET)
                startActivity(intent)
            } catch (e: Exception) {
                Browser.openUrl(requireContext(), APP_WEB)
            }
            otherAppsRow -> try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(ACCOUNT_MARKET)
                startActivity(intent)
            } catch (e: Exception) {
                Browser.openUrl(requireContext(), ACCOUNT_WEB)
            }
            libsRow -> (requireActivity() as AboutActivity).startFragment(LibsFragment(), container.id, LIBS_FRAGMENT_TAG)
            feedbackRow -> try {
                val packageManager = requireContext().packageManager
                val packageInfo = packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0)
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
                e.printStackTrace()
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

    inner class AboutAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var iconView: ImageView? = null
        private var textView: AppCompatTextView? = null
        private var appNameText: AppCompatTextView? = null
        private var versionText: AppCompatTextView? = null
        private var poweredText: AppCompatTextView? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when (viewType) {
                0 -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_about, parent, false)
                    val holder = HeaderVH(view)
                    appNameText = view.findViewById(R.id.appName)
                    versionText = view.findViewById(R.id.versionText)
                    view.setOnClickListener(object : DebouncingOnClickListener() {
                        override fun doClick(v: View) {
                            val position = holder.adapterPosition
                            doAction(position)
                        }
                    })
                    return holder
                }
                1 -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_powered, parent, false)
                    val holder = AboutVH(view)
                    poweredText = view.findViewById(R.id.poweredText)
                    view.setOnClickListener(object : DebouncingOnClickListener() {
                        override fun doClick(v: View) {
                            val position = holder.adapterPosition
                            doAction(position)
                        }
                    })
                    return holder
                }
                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cell, parent, false)
                    val holder = FooterVH(view)
                    iconView = view.findViewById(R.id.icon_view)
                    textView = view.findViewById(R.id.text_view)
                    view.setOnClickListener(object: DebouncingOnClickListener() {
                        override fun doClick(v: View) {
                            val position = holder.adapterPosition
                            doAction(position)
                        }
                    })
                    return holder
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val type = getItemViewType(position)

            when (type) {
                0 -> {
                    appNameText!!.text = getString(R.string.app_for_android, getString(R.string.app_name))
                    versionText!!.text = getString(R.string.version_build_date, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, BuildConfig.VERSION_DATE)
                }
                1 -> poweredText!!.text = SpannableUtil.replaceTags(getString(R.string.powered_by))
                else -> when (position) {
                    rateGooglePlay -> {
                        iconView!!.setImageResource(R.drawable.ic_google_play)
                        textView!!.setText(R.string.rate_google_play)
                    }
                    forkGithubRow -> {
                        iconView!!.setImageResource(R.drawable.ic_github)
                        textView!!.setText(R.string.fork_github)
                    }
                    libsRow -> {
                        iconView!!.setImageResource(R.drawable.ic_storage)
                        textView!!.setText(R.string.open_source_libs)
                    }
                    otherAppsRow -> {
                        iconView!!.setImageResource(R.drawable.ic_shop)
                        textView!!.setText(R.string.other_developer_apps)
                    }
                    feedbackRow -> {
                        iconView!!.setImageResource(R.drawable.ic_mail)
                        textView!!.setText(R.string.feedback)
                    }
                    shareFriendsRow -> {
                        iconView!!.setImageResource(R.drawable.ic_share)
                        textView!!.setText(R.string.share_with_friends)
                    }
                    donatePaypalRow -> {
                        iconView!!.setImageResource(R.drawable.ic_paypal)
                        textView!!.setText(R.string.donate_paypal)
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return rowCount
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                infoRow -> 0
                poweredByRow -> 1
                else -> 2
            }
        }

        internal inner class HeaderVH(itemView: View): RecyclerView.ViewHolder(itemView)

        internal inner class AboutVH(itemView: View): RecyclerView.ViewHolder(itemView)

        internal inner class FooterVH(itemView: View): RecyclerView.ViewHolder(itemView)
    }
}