package com.example.car_damage_diagnosis.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Настройки") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.End // Выравнивание по правому краю
        ) {
            // Параметр 1
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Параметр 1")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = viewModel.setting1, onCheckedChange = viewModel::updateSetting1)
            }

            // Параметр 2
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Параметр 2")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = viewModel.setting2, onCheckedChange = viewModel::updateSetting2)
            }
        }
    }
}