package com.example.car_damage_diagnosis.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.car_damage_diagnosis.R

@Composable
fun PhoneLoginScreen(
    onNavigateToVerification: (String) -> Unit,
    onBackToWelcome: () -> Unit,
    viewModel: PhoneLoginViewModel = hiltViewModel()
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val isValidPhoneNumber by viewModel.isValidPhoneNumber.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 150.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_1),
                contentDescription = "Phone Login Image",
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .width(160.dp)
                    .height(160.dp)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    viewModel.updatePhoneNumber(it.filter { char -> char.isDigit() })
                },
                label = { Text("Номер телефона") },
                prefix = { Text("+7 ") },
                placeholder = { Text("") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true,
                maxLines = 1,
                isError = phoneNumber.isNotEmpty() && phoneNumber.length < 10,
                modifier = Modifier
                    .width(380.dp)
                    .heightIn(min = 50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF65558F),
                    unfocusedBorderColor = Color(0xFF79747E),
                    focusedLabelColor = Color(0xFF65558F),
                    unfocusedLabelColor = Color(0xFF79747E)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.sendVerificationCode()
                    onNavigateToVerification(phoneNumber)
                },
                enabled = isValidPhoneNumber && !isLoading,
                modifier = Modifier
                    .width(380.dp)
                    .height(50.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF65558F),
                    contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Получить код")
                }
            }
        }
    }
}
