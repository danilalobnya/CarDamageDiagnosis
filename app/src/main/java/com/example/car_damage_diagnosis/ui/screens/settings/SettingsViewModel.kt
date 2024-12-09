package com.example.car_damage_diagnosis.ui.screens.settings

import android.content.Context
import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    var setting1 by mutableStateOf(getSettingFromSharedPrefs("setting1", false))
        private set

    var setting2 by mutableStateOf(getSettingFromSharedPrefs("setting2", true))
        private set

    fun updateSetting1(newValue: Boolean) {
        viewModelScope.launch(Dispatchers.Main) { // Use viewModelScope and Dispatchers.Main
            setting1 = newValue
            saveSettingToSharedPrefs("setting1", newValue)
        }
    }

    fun updateSetting2(newValue: Boolean) {
        viewModelScope.launch(Dispatchers.Main) { // Use viewModelScope and Dispatchers.Main
            setting2 = newValue
            saveSettingToSharedPrefs("setting2", newValue)
        }
    }

    private fun getSettingFromSharedPrefs(key: String, defaultValue: Boolean): Boolean {
        val sharedPrefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    private fun saveSettingToSharedPrefs(key: String, value: Boolean) {
        val sharedPrefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
}