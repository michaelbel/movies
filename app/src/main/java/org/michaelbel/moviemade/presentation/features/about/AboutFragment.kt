package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.crashlytics.android.Crashlytics
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.analytics.Analytics
import org.michaelbel.core.analytics.Analytics.EVENT_ACTION_CLICK
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
import org.michaelbel.moviemade.core.getViewModel
import org.michaelbel.moviemade.core.reObserve
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.sources.SourcesFragment

class AboutFragment: BaseFragment() {

    companion object {
        private const val TELEGRAM_PACKAGE_NAME = "org.telegram.messenger"
        private const val APP_UPDATE_REQUEST_CODE = 189

        internal fun newInstance() = AboutFragment()
    }

    private val adapter = ListAdapter()

    /**
     * Таким образом, ViewModel будет создан только в том случае, если он еще не существует в той же области.
     * Если он уже существует, библиотека вернет тот же экземпляр, который уже использовала.
     * Таким образом, даже если вы не используете lazy делегата и делаете этот вызов в onCreate,
     * вы гарантированно получите один и тот же ViewModel каждый раз. Он будет создан только один раз. Это замечательно!
     */
    private val viewModel: AboutModel by lazy { getViewModel<AboutModel>() }

    private var appUpdateManager: AppUpdateManager? = null
    private var appUpdateInfo: Task<AppUpdateInfo>? = null

    private val installStateUpdatedListener = InstallStateUpdatedListener {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.about)
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.items.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { cell ->
                when (cell) {
                    "update" -> appUpdateManager?.startUpdateFlowForResult(appUpdateInfo?.result, AppUpdateType.IMMEDIATE, requireActivity(), APP_UPDATE_REQUEST_CODE)
                    "rate" -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = APP_MARKET.toUri()
                            startActivity(intent)
                        } catch (e: Exception) {
                            Browser.openUrl(requireContext(), APP_WEB)
                            Crashlytics.logException(e)
                        }

                        //Analytics.logEvent(EVENT_ACTION_CLICK, "Rate button click")
                    }
                    "fork" -> {
                        Browser.openUrl(requireContext(), GITHUB_URL)
                        //Analytics.logEvent(EVENT_ACTION_CLICK, "Fork button click")
                    }
                    "libs" -> {
                        requireFragmentManager().transaction {
                            add((requireActivity() as ContainerActivity).container.id, SourcesFragment.newInstance())
                            addToBackStack(tag)
                        }
                    }
                    "apps" -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = ACCOUNT_MARKET.toUri()
                            startActivity(intent)
                        } catch (e: Exception) {
                            Browser.openUrl(requireContext(), ACCOUNT_WEB)
                            Crashlytics.logException(e)
                        }

                        Analytics.logEvent(EVENT_ACTION_CLICK, "Apps button click")
                    }
                    "feedback" -> {
                        try {
                            val packageInfo = requireContext().packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0)
                            if (packageInfo != null) {
                                val telegram = Intent(Intent.ACTION_VIEW, TELEGRAM_URL.toUri())
                                startActivity(telegram)
                            } else {
                                feedbackEmail()
                            }
                        } catch (e: PackageManager.NameNotFoundException) {
                            feedbackEmail()
                            Crashlytics.logException(e)
                        }

                        Analytics.logEvent(EVENT_ACTION_CLICK, "Feedback button click")
                    }
                    "share" -> {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, APP_WEB)
                        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
                    }
                    "donate" -> {
                        Browser.openUrl(requireContext(), PAYPAL_ME)
                        Analytics.logEvent(EVENT_ACTION_CLICK, "Donate button click")
                    }
                    else -> return@let
                }
            }
        })

        // IAUs
        appUpdateManager = AppUpdateManagerFactory.create(requireContext())
        appUpdateManager?.registerListener(installStateUpdatedListener)

        appUpdateInfo = appUpdateManager?.appUpdateInfo
        appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                viewModel.addAppUpdateItem()
            }
        }
        appUpdateInfo?.addOnFailureListener { Crashlytics.logException(it) }
    }

    private fun feedbackEmail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, EMAIL)
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(intent, getString(R.string.feedback)))
    }
}