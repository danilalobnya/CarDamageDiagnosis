package com.example.car_damage_diagnosis.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.car_damage_diagnosis.R


@Composable
fun VerifyCodeScreen(
    phoneNumber: String,
    onNavigateToHome: () -> Unit,
    onBackToPhone: () -> Unit,
    viewModel: VerifyCodeViewModel = hiltViewModel()
) {
    val verificationCode by viewModel.verificationCode.collectAsState()
    LaunchedEffect(phoneNumber) {
        viewModel.setPhoneNumber(phoneNumber)
    }

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
                painter = painterResource(id = R.drawable.frame_2),
                contentDescription = "Phone Login Image",
                modifier = Modifier
                    .width(160.dp)
                    .height(160.dp)
                    .padding(bottom = 40.dp)
            )

            FourDigitCodeInput(
                code = verificationCode,
                onCodeChange = { viewModel.updateVerificationCode(it, onNavigateToHome) },
                viewModel = viewModel
            )

            TextButton(
                onClick = { viewModel.resendCode() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Отправить код повторно",
                    color = Color(0xFF65558F)
                )
            }
        }
    }
}

@Composable
fun FourDigitCodeInput(
    code: String,
    onCodeChange: (String) -> Unit,
    viewModel: VerifyCodeViewModel = hiltViewModel()
) {
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }
    val isError by viewModel.isError.collectAsState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .width(228.dp)
            .height(50.dp)
    ) {
        for (i in 0..3) {
            OutlinedTextField(
                value = if (code.length > i) code[i].toString() else "",
                onValueChange = { newChar ->
                    if (newChar.isEmpty()) {
                        // Handle backspace
                        onCodeChange(code.substring(0, maxOf(0, code.length - 1)))
                        if (i > 0) {
                            focusRequesters[i - 1].requestFocus()
                        }
                    } else if (newChar.length == 1 && newChar[0].isDigit()) {
                        val newCode = code.substring(0, minOf(i, code.length)) + newChar
                        onCodeChange(newCode)
                        if (i < 3) {
                            focusRequesters[i + 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .width(42.dp)
                    .height(50.dp)
                    .focusRequester(focusRequesters[i]),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    lineHeight = 26.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color(0xFF79747E),
                    focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color(0xFF65558F)
                ),
                isError = isError
            )
        }
    }
}
