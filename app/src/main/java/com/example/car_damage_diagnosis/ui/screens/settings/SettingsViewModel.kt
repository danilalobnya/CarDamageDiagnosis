package com.example.car_damage_diagnosis.ui.screens.settings

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_damage_diagnosis.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class User(
    val name: String = "",
    val surname: String = "",
    val birthDate: String = "",
    val phoneNumber: String = "",
    val avatarUrl: String? = null
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    // State для хранения данных пользователя
    private val _user = MutableStateFlow(loadUserFromSharedPrefs())
    val user: StateFlow<User> = _user.asStateFlow()

    // State для управления отображением диалогов
    private val _showEditProfileDialog = MutableStateFlow(false)
    val showEditProfileDialog: StateFlow<Boolean> = _showEditProfileDialog.asStateFlow()

    private val _showEditPhoneNumberDialog = MutableStateFlow(false)
    val showEditPhoneNumberDialog: StateFlow<Boolean> = _showEditPhoneNumberDialog.asStateFlow()

    private val _showThemeDialog = MutableStateFlow(false)
    val showThemeDialog: StateFlow<Boolean> = _showThemeDialog.asStateFlow()

    // State для управления темой приложения
    private val _selectedTheme = MutableStateFlow(loadThemeFromSharedPrefs())
    val selectedTheme: StateFlow<Theme> = _selectedTheme.asStateFlow()

    // State для хранения номера телефона и его валидности
    private val _phoneNumber = MutableStateFlow(getPhoneNumberFromSharedPrefs())
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()
    private val _isValidPhoneNumber = MutableStateFlow(false)
    val isValidPhoneNumber: StateFlow<Boolean> = _isValidPhoneNumber.asStateFlow()

    // State для форматированного номера телефона (без +7)
    val formattedPhoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    enum class Theme { Dark, Light, System }


    // Функции для управления отображением диалогов
    fun onEditProfileClick() {
        _showEditProfileDialog.value = true
    }

    fun onDismissEditProfileDialog() {
        _showEditProfileDialog.value = false
    }

    fun onEditPhoneNumberClick() {
        _showEditPhoneNumberDialog.value = true
    }

    fun onDismissEditPhoneNumberDialog() {
        _showEditPhoneNumberDialog.value = false
    }

    fun onThemeClick() {
        _showThemeDialog.value = true
    }

    fun onDismissThemeDialog() {
        _showThemeDialog.value = false
    }

    // Функции для сохранения и загрузки данных
    private fun loadUserFromSharedPrefs(): User {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        return User(
            name = prefs.getString("name", "") ?: "",
            surname = prefs.getString("surname", "") ?: "",
            birthDate = prefs.getString("birthDate", "") ?: "",
            phoneNumber = prefs.getString("phoneNumber", "") ?: "",
            avatarUrl = prefs.getString("avatarUrl", null)
        )
    }
    private fun saveProfileToSharedPrefs(user: User){
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("name", user.name)
            .putString("surname", user.surname)
            .putString("birthDate", user.birthDate)
            .apply()
    }

    fun updateProfile(name: String, surname: String, birthDate: String){
        _user.value = _user.value.copy(name = name, surname = surname, birthDate = birthDate)
        saveProfileToSharedPrefs(_user.value)
    }

    private fun getPhoneNumberFromSharedPrefs(): String {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        return prefs.getString("phoneNumber", "") ?: ""
    }


    private fun savePhoneNumberToSharedPrefs(phoneNumber: String) {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("phoneNumber", phoneNumber).apply()
    }
    fun updatePhoneNumber(input: String) {
        val digitsOnly = input.filter { it.isDigit() }.take(10)
        _phoneNumber.value = digitsOnly
        _isValidPhoneNumber.value = digitsOnly.length == 10
        savePhoneNumberToSharedPrefs(digitsOnly)
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

    // Функция для установки темы приложения
    fun onThemeSelected(theme: Theme) {
        _selectedTheme.value = theme
        saveThemeToSharedPrefs(theme)
    }


    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog.asStateFlow()

    // Обработчики нажатия на кнопку выйти

    fun onDismissLogoutDialog() {
        _showLogoutDialog.value = false
    }
    // Обработчики нажатия на кнопку выйти
    fun onLogoutClick() {
        _showLogoutDialog.value = true
    }
    fun logout(){
        _showLogoutDialog.value = false
        clearSharedPrefs()
        restartApp()
    }
    private fun clearSharedPrefs(){
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    private fun restartApp(){
        val intent = Intent(context,  MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}