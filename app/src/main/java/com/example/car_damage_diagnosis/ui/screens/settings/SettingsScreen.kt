package com.example.car_damage_diagnosis.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.car_damage_diagnosis.R

data class SettingItem(val title: String, val icon: Int, val onClick: () -> Unit)

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(topBar = { TopAppBar(title = { Text("Настройки") }) }) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            UserSection(phoneNumber = viewModel.user.value?.phoneNumber ?: "", onEditClick = viewModel::onEditProfileClick)
            SettingsList(
                settings = listOf(
                    SettingItem("Данные", R.drawable.ic_person, viewModel::onEditProfileClick),
                    SettingItem("Тема", R.drawable.ic_brightness, viewModel::onThemeClick),
                    SettingItem("Выход", R.drawable.ic_logout, viewModel::onLogoutClick)
                )
            )
        }
    }
}


@Composable
fun UserSection(phoneNumber: String, onEditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center avatar horizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_avatatr),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .padding(bottom = 16.dp), // Add padding below avatar
            contentScale = ContentScale.Crop
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onEditClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_phone_number), // Use Edit icon
                    contentDescription = "Edit Profile"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text("+7" + phoneNumber, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SettingsList(settings: List<SettingItem>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(settings) { item ->
            SettingsItem(item = item)
        }
    }
}

@Composable
fun SettingsItem(item: SettingItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { item.onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))
                Text(item.title)
            }
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Next")
        }
    }
}