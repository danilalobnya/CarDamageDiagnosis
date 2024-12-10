package com.example.car_damage_diagnosis.ui.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class User(val phoneNumber: String, val avatarUrl: String? = null)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(loadUserFromSharedPrefs())
    val user: StateFlow<User?> = _user

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserToSharedPrefs(user)
            _user.value = user
        }
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

    fun onEditProfileClick() {
        // Навигация на экран редактирования профиля
    }

    fun onThemeClick() {
        // Обработка клика на "Тема"
    }

    fun onLogoutClick() {
        // Логика выхода из аккаунта
    }

    fun getFullPhoneNumber(): String = user.value?.phoneNumber?.let { "+7$it" } ?: ""
}