package com.example.car_damage_diagnosis.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneLoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _isValidPhoneNumber = MutableStateFlow(false)
    val isValidPhoneNumber: StateFlow<Boolean> = _isValidPhoneNumber.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updatePhoneNumber(input: String) {
        val digitsOnly = input.filter { it.isDigit() }.take(10)
        _phoneNumber.value = digitsOnly
        _isValidPhoneNumber.value = digitsOnly.length == 10
        savePhoneNumberToSharedPrefs(digitsOnly)
    }

    private fun savePhoneNumberToSharedPrefs(phoneNumber: String) {
        val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("phoneNumber", phoneNumber).apply()
    }

    fun getFullPhoneNumber(): String = "+7${_phoneNumber.value}"

    fun sendVerificationCode() {
        viewModelScope.launch {
            _isLoading.value = true
            // TODO: Implement actual verification code sending logic
            _isLoading.value = false
        }
    }
}