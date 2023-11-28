package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var THEMEPREFKEY: String
    private lateinit var NOTIFICATIONPREFKEY: String

    private lateinit var themePref: ListPreference
    private lateinit var notificationPref: SwitchPreference

    private lateinit var dailyReminder: DailyReminder

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
        //TODO 10 : Update theme based on value in ListPreference
        themePref.setOnPreferenceChangeListener { preference, newValue ->
            val themeMode = NightMode.valueOf(newValue.toString().toUpperCase(Locale.US))
            updateTheme(themeMode.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        notificationPref.setOnPreferenceChangeListener { preference, newValue ->
            when(newValue){
                true -> dailyReminder.setDailyReminder(requireContext())
                false -> dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun init(){
        THEMEPREFKEY = resources.getString(R.string.pref_key_dark)
        NOTIFICATIONPREFKEY = resources.getString(R.string.pref_key_notify)

        themePref = findPreference<ListPreference>(THEMEPREFKEY) as ListPreference
        notificationPref = findPreference<SwitchPreference>(NOTIFICATIONPREFKEY) as SwitchPreference

        dailyReminder = DailyReminder()
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}