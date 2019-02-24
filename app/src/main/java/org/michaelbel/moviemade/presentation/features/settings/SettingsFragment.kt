package org.michaelbel.moviemade.presentation.features.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.features.about.AboutActivity

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: androidx.preference.Preference?): Boolean {
        if (preference?.key == "key_about") {
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
            return true
        }

        return super.onPreferenceTreeClick(preference)
    }
}