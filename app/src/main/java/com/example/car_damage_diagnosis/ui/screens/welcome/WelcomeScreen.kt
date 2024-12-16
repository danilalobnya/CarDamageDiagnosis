package com.example.car_damage_diagnosis.ui.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.car_damage_diagnosis.ui.components.AuthButton
import com.example.car_damage_diagnosis.ui.screens.login.PhoneLoginScreen
import com.example.car_damage_diagnosis.ui.screens.login.VerifyCodeScreen

@Composable
fun WelcomeScreen(
    onNavigateToHome: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    var screenState by remember { mutableStateOf(WelcomeScreenState.PHONE_LOGIN) }
    var phoneNumber by remember { mutableStateOf("") }

    when (screenState) {
        WelcomeScreenState.INITIAL -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Диагностика повреждений автомобилей",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Проанализируйте повреждения автомобилей при помощи AI",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                AuthButton(
                    text = "Войти",
                    onClick = { screenState = WelcomeScreenState.PHONE_LOGIN }
                )
            }
        }

        WelcomeScreenState.PHONE_LOGIN -> {
            PhoneLoginScreen(
                onNavigateToVerification = { number ->
                    phoneNumber = number
                    screenState = WelcomeScreenState.VERIFY_CODE
                },
                onBackToWelcome = { 
                    screenState = WelcomeScreenState.INITIAL 
                }
            )
        }

        WelcomeScreenState.VERIFY_CODE -> {
            VerifyCodeScreen(
                phoneNumber = phoneNumber,
                onNavigateToHome = onNavigateToHome,
                onBackToPhone = { 
                    screenState = WelcomeScreenState.PHONE_LOGIN 
                }
            )
        }
    }
}

enum class WelcomeScreenState {
    INITIAL,
    PHONE_LOGIN,
    VERIFY_CODE
}
