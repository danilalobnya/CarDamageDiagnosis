package com.example.car_damage_diagnosis.ui.screens.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class User(val phoneNumber: String, val avatarUrl: String? = null)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(loadUserFromSharedPrefs())
    val user: StateFlow<User?> = _user

    val formattedPhoneNumber: StateFlow<String> = _user.map { it?.phoneNumber ?: "" }
        .map { "+7$it" }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _showEditProfileDialog = MutableStateFlow(false)
    val showEditProfileDialog: StateFlow<Boolean> = _showEditProfileDialog

    private val _showThemeDialog = MutableStateFlow(false)
    val showThemeDialog: StateFlow<Boolean> = _showThemeDialog

    private val _selectedTheme = MutableStateFlow(loadThemeFromSharedPrefs())
    val selectedTheme: StateFlow<Theme> = _selectedTheme

    enum class Theme { Dark, Light, System }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserToSharedPrefs(user)
            _user.value = user
        }
    }

    fun onEditProfileClick() {
        _showEditProfileDialog.value = true
    }

    fun onDismissEditProfileDialog() {
        _showEditProfileDialog.value = false
    }

    fun onThemeClick() {
        _showThemeDialog.value = true
    }

    fun onDismissThemeDialog() {
        _showThemeDialog.value = false
    }

    fun onThemeSelected(theme: Theme) {
        _selectedTheme.value = theme
        saveThemeToSharedPrefs(theme)
    }


    private fun loadUserFromSharedPrefs(): User? {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        val phoneNumber = prefs.getString("phoneNumber", null)
        val avatarUrl = prefs.getString("avatarUrl", null)
        return User(phoneNumber ?: "", avatarUrl)
    }

    private fun saveUserToSharedPrefs(user: User) {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("phoneNumber", user.phoneNumber)
            putString("avatarUrl", user.avatarUrl)
            apply()
        }
    }

    private fun saveUserFromSharedPrefs(user: User) {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("phoneNumber", user.phoneNumber)
            putString("avatarUrl", user.avatarUrl)
            apply()
        }
    }

    private fun saveThemeToSharedPrefs(theme: Theme) {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("theme", theme.name).apply()
    }

    private fun loadThemeFromSharedPrefs(): Theme {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        return when (prefs.getString("theme", "System")) {
            "Dark" -> Theme.Dark
            "Light" -> Theme.Light
            else -> Theme.System
        }
    }

    fun onLogoutClick() {
        // Логика выхода из аккаунта
    }

    fun getFullPhoneNumber(): String = user.value?.phoneNumber?.let { "+7$it" } ?: ""


}