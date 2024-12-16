package com.example.car_damage_diagnosis.ui.screens.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.car_damage_diagnosis.R
import dagger.hilt.android.AndroidEntryPoint

data class SettingItem(val title: String, val icon: Int, val onClick: () -> Unit)

@AndroidEntryPoint
class SettingsScreen : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombinedSettingsScreen(onBackClick = { onBackPressed() }) // Pass onBackPressed
        }
    }


@Composable
fun CombinedSettingsScreen(viewModel: SettingsViewModel = hiltViewModel(), onBackClick: () -> Unit) {
    val phoneNumber by viewModel.formattedPhoneNumber.collectAsState()
    val showDialog by viewModel.showEditProfileDialog.collectAsState()
    val showThemeDialog by viewModel.showThemeDialog.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var selectedTheme by remember { mutableStateOf(SettingsViewModel.Theme.System) }
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Настройки", textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())


            // UserSection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatatr),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .padding(bottom = 16.dp)
                )

                Surface(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(100.dp),
                    color = Color(0xFFEADDFF),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(30.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFEADDFF),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { viewModel.onEditProfileClick() }, modifier = Modifier.size(18.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_edit_phone_number),
                                        contentDescription = "Edit Profile",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color(0xFF65558F)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(phoneNumber, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
            // SettingsList
            Column {
                listOf(
                    SettingItem("Данные", R.drawable.ic_person) { viewModel.onEditProfileClick() },
                    SettingItem("Тема", R.drawable.ic_brightness, viewModel::onThemeClick),
                    SettingItem("Выход", R.drawable.ic_logout, viewModel::onLogoutClick)
                ).forEach { item ->

                    //SettingsItem
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { item.onClick() }
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(item.title)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "Next")
                        }
                        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            // EditProfileDialog
            if (showDialog) {
                Dialog(onDismissRequest = viewModel::onDismissEditProfileDialog) {
                    Surface(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 2.dp,
                        color = Color(0xFFEADDFF)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Редактирование профиля", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(16.dp))

                            // OutlinedTextFieldSimple for name

                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it},
                                label = { Text("Имя") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            // OutlinedTextFieldSimple for surname

                            OutlinedTextField(
                                value = surname,
                                onValueChange = { surname = it },
                                label = { Text("Фамилия") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            // OutlinedTextFieldSimple for birthDate

                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = { birthDate = it },
                                label = { Text("Дата рождения") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick = viewModel::onDismissEditProfileDialog) { Text("Отменить") }
                                Button(onClick = {
                                    viewModel.onDismissEditProfileDialog()
                                }) { Text("Сохранить") }
                            }
                        }
                    }
                }
            }


            // ThemeDialog
            if (showThemeDialog) {
                Dialog(onDismissRequest = viewModel::onDismissThemeDialog) {
                    Surface(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 2.dp,
                        color = Color(0xFFEADDFF)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Тема приложения", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(16.dp))
                            SettingsViewModel.Theme.values().forEach { theme ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                        .clickable { selectedTheme = theme }
                                ) {
                                    RadioButton(
                                        selected = selectedTheme == theme,
                                        onClick = { selectedTheme = theme }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(when (theme) {
                                        SettingsViewModel.Theme.Dark -> "Темная"
                                        SettingsViewModel.Theme.Light -> "Светлая"
                                        SettingsViewModel.Theme.System -> "Цвет системы"
                                    })
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.onThemeSelected(selectedTheme); viewModel.onDismissThemeDialog() }) {
                                Text("Сохранить")
                            }
                        }
                    }
                }
            }
        }
    }
}
}
