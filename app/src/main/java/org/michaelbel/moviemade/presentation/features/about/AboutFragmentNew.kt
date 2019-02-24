package org.michaelbel.moviemade.presentation.features.about

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.*
import timber.log.Timber

class AboutFragmentNew: PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().toolbar.setNavigationOnClickListener { requireActivity().finish() }
        (requireActivity() as AboutActivity).supportActionBar?.setTitle(R.string.about)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(org.michaelbel.moviemade.R.xml.preference_about, rootKey)

        /*if (rootKey == "key_about") {
            appName.text = getString(R.string.app_for_android, getString(R.string.app_name))
            versionText.text = getString(R.string.version_build_date, VERSION_NAME, VERSION_CODE, VERSION_DATE)
        } else if (rootKey == "key_powered") {
            poweredText.text = SpannableUtil.replaceTags(getString(R.string.powered_by))
        }*/
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when {
            preference?.key == "key_rate" -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(APP_MARKET)
                    startActivity(intent)
                } catch (e: Exception) {
                    Browser.openUrl(requireContext(), APP_WEB)
                }
                return true
            }
            preference?.key == "key_fork" -> Browser.openUrl(requireContext(), GITHUB_URL)
            preference?.key == "key_sources" -> (requireActivity() as AboutActivity).startFragment(LibsFragment(), org.michaelbel.moviemade.R.id.fragment_view, "libsFragment")
            preference?.key == "key_feedback" ->
                try {
                    val packageManager = requireContext().packageManager
                    val packageInfo = packageManager.getPackageInfo("org.telegram.messenger", 0)
                    if (packageInfo != null) {
                        val telegram = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL))
                        startActivity(telegram)
                    } else {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_EMAIL, EMAIL)
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(org.michaelbel.moviemade.R.string.subject))
                        intent.putExtra(Intent.EXTRA_TEXT, "")
                        startActivity(Intent.createChooser(intent, getString(org.michaelbel.moviemade.R.string.feedback)))
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    Timber.e(e)
                }
            preference?.key == "key_share" -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, APP_WEB)
                startActivity(Intent.createChooser(intent, getString(org.michaelbel.moviemade.R.string.share_via)))
            }
            preference?.key == "key_apps" -> try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(ACCOUNT_MARKET)
                startActivity(intent)
            } catch (e: Exception) {
                Browser.openUrl(requireContext(), ACCOUNT_WEB)
            }
            preference?.key == "key_donate" -> Browser.openUrl(requireContext(), PAYPAL_ME)
        }

        return super.onPreferenceTreeClick(preference)
    }
}