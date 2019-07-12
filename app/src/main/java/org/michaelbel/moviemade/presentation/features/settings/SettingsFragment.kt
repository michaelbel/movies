package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.ABOUT
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.FRAGMENT_NAME

class SettingsFragment: PreferenceFragmentCompat() {

    companion object {
        internal fun newInstance() = SettingsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: androidx.preference.Preference?): Boolean {
        if (preference?.key == "key_about") {
            requireActivity().startActivity<ContainerActivity> {
                putExtra(FRAGMENT_NAME, ABOUT)
            }
            return true
        }

        return super.onPreferenceTreeClick(preference)
    }
}