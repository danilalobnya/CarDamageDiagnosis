package com.example.car_damage_diagnosis.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyCodeViewModel @Inject constructor() : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _verificationCode = MutableStateFlow("")
    val verificationCode: StateFlow<String> = _verificationCode.asStateFlow()

    private val _isValidCode = MutableStateFlow(false)
    val isValidCode: StateFlow<Boolean> = _isValidCode.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Hardcoded verification code for testing
    private val correctCode = "1234"

    fun setPhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun updateVerificationCode(code: String, onSuccess: () -> Unit) {
        // Limit to 4 digits
        val cleanCode = code.filter { it.isDigit() }.take(4)
        _verificationCode.value = cleanCode
        
        // Check if code is complete (4 digits)
        if (cleanCode.length == 4) {
            // Validate against correct code
            _isValidCode.value = cleanCode == correctCode
            _isError.value = cleanCode != correctCode
            
            if (_isValidCode.value) {
                viewModelScope.launch {
                    delay(200) // Small delay before navigation
                    onSuccess()
                }
            }
        } else {
            _isValidCode.value = false
            _isError.value = false
        }
    }

    fun verifyCode(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            // TODO: Implement actual code verification logic
            delay(2000) // Simulate network call
            _isLoading.value = false
            onSuccess()
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            _isLoading.value = true
            _verificationCode.value = "" // Clear the verification code
            _isError.value = false // Reset error state
            // TODO: Implement actual code resend logic
            delay(2000) // Simulate network call
            _isLoading.value = false
        }
    }
}
