package com.example.car_damage_diagnosis.ui.screens.loader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.car_damage_diagnosis.ui.screens.history.DetailActivity

class LoaderScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoaderScreenContent()
        }

        // Задержка 5 секунд
        Handler(Looper.getMainLooper()).postDelayed({
            // Переход на главный экран после 5 секунд
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
            finish() // Закрыть текущую активность, чтобы не возвращаться на экран загрузки
        }, 5000) // 5000 миллисекунд = 5 секунд
    }
}

@Composable
fun LoaderScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Анализируется...")
    }
}