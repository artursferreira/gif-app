package com.artur.gifapp.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.artur.gifapp.R
import com.artur.gifapp.extensions.getAppTheme

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val preference: Preference? = findPreference(getString(R.string.pref_key_night))
        preference?.onPreferenceChangeListener = modeChangeListener
    }

    private val modeChangeListener =
        Preference.OnPreferenceChangeListener { _, newValue ->
            newValue?.let {
                updateTheme(requireContext().getAppTheme(it as String))
            }

            true
        }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}