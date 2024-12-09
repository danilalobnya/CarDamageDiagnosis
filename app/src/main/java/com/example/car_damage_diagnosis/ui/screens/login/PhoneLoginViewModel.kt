package com.example.car_damage_diagnosis.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneLoginViewModel @Inject constructor() : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _isValidPhoneNumber = MutableStateFlow(false)
    val isValidPhoneNumber: StateFlow<Boolean> = _isValidPhoneNumber.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updatePhoneNumber(input: String) {
        // Оставляем только цифры и ограничиваем 10 символами
        val digitsOnly = input.filter { it.isDigit() }.take(10)
        
        _phoneNumber.value = digitsOnly
        _isValidPhoneNumber.value = digitsOnly.length == 10
    }

    fun getFullPhoneNumber(): String = "+7$phoneNumber.value"

    fun sendVerificationCode() {
        viewModelScope.launch {
            _isLoading.value = true
            // TODO: Implement actual verification code sending logic
            _isLoading.value = false
        }
    }
}
