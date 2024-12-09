package com.example.car_damage_diagnosis.ui.navigation

object Screen {
    const val Welcome = "welcome"
    const val PhoneLogin = "phone_login"
    const val VerifyCode = "verify_code"
    const val Home = "home"
    const val CameraCapture = "camera_capture"
    const val DamageAnalysis = "damage_analysis"
    const val History = "history"
    const val Profile = "profile"
    const val Settings = "settings"

    fun verifyCodeRoute(phoneNumber: String) = "${VerifyCode}/$phoneNumber"
}
