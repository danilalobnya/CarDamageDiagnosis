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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
    val showProfileDialog by viewModel.showEditProfileDialog.collectAsState()
    val showThemeDialog by viewModel.showThemeDialog.collectAsState()
    val showPhoneNumber by viewModel.showEditPhoneNumberDialog.collectAsState()
    val showLogoutDialog by viewModel.showLogoutDialog.collectAsState()

    // Состояния для хранения значений в диалогах
    var tempName by rememberSaveable { mutableStateOf("") }
    var tempSurname by rememberSaveable { mutableStateOf("") }
    var tempBirthDate by rememberSaveable { mutableStateOf("") }
    var tempSelectedTheme by remember { mutableStateOf(SettingsViewModel.Theme.System) }
    var tempPhoneNumber by rememberSaveable { mutableStateOf(viewModel.phoneNumber.value) }

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
                        .height(30.dp)
                        .clickable { viewModel.onEditPhoneNumberClick() },// Добавлен clickable к Surface
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
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit_phone_number),
                                    contentDescription = "Edit Profile",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color(0xFF65558F)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    // Показываем номер без +7 так как добавляем в prefix
                                    "+7" + tempPhoneNumber,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
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
            if (showProfileDialog) {
                Dialog(onDismissRequest = {
                    viewModel.onDismissEditProfileDialog()
                    //  При отмене сбрасываем состояние на начальное
                    tempName = viewModel.user.value.name
                    tempSurname = viewModel.user.value.surname
                    tempBirthDate = viewModel.user.value.birthDate
                }) {
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
                                value = tempName,
                                onValueChange = { tempName = it },
                                label = { Text("Имя") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isError = tempName.length < 2 || tempName.length > 15,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF65558F),
                                    unfocusedBorderColor = Color(0xFF79747E),
                                    focusedLabelColor = Color(0xFF65558F),
                                    unfocusedLabelColor = Color(0xFF79747E)
                                )
                            )
                            // OutlinedTextFieldSimple for surname
                            OutlinedTextField(
                                value = tempSurname,
                                onValueChange = { tempSurname = it },
                                label = { Text("Фамилия") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isError = tempSurname.length < 2 || tempSurname.length > 15,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF65558F),
                                    unfocusedBorderColor = Color(0xFF79747E),
                                    focusedLabelColor = Color(0xFF65558F),
                                    unfocusedLabelColor = Color(0xFF79747E)
                                )
                            )

                            // OutlinedTextFieldSimple for birthDate
                            OutlinedTextField(
                                value = tempBirthDate,
                                onValueChange = {
                                    // ограничиваем ввод до 10 символов
                                    if(it.length <= 10) {
                                        tempBirthDate =  it.filter { char -> char.isDigit() || char == '/' }.let { input ->
                                            if (input.length == 2 || input.length == 5) {
                                                if (input.last() != '/') {
                                                    "$input/"
                                                } else input
                                            }else input
                                        }
                                    }

                                },
                                label = { Text("Дата рождения") },
                                placeholder = { Text("дд/мм/гггг") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isError =  !isValidDate(tempBirthDate) && tempBirthDate.isNotEmpty(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF65558F),
                                    unfocusedBorderColor = Color(0xFF79747E),
                                    focusedLabelColor = Color(0xFF65558F),
                                    unfocusedLabelColor = Color(0xFF79747E)
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick = {
                                    viewModel.onDismissEditProfileDialog()
                                    tempName = viewModel.user.value.name
                                    tempSurname = viewModel.user.value.surname
                                    tempBirthDate = viewModel.user.value.birthDate
                                }) { Text("Отменить") }
                                Button(
                                    onClick = {
                                        viewModel.updateProfile(tempName, tempSurname, tempBirthDate)
                                        viewModel.onDismissEditProfileDialog()
                                    },
                                    enabled = tempName.length in 2..15 &&
                                            tempSurname.length in 2..15 &&
                                            isValidDate(tempBirthDate)
                                )
                                { Text("Сохранить") }
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
                                        .clickable { tempSelectedTheme = theme }
                                ) {
                                    RadioButton(
                                        selected = tempSelectedTheme == theme,
                                        onClick = { tempSelectedTheme = theme }
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
                            Button(onClick = { viewModel.onThemeSelected(tempSelectedTheme); viewModel.onDismissThemeDialog() }) {
                                Text("Сохранить")
                            }
                        }
                    }
                }
            }
            //EditPhoneNumber
            if (showPhoneNumber) {
                // Используем LaunchedEffect для установки начального значения tempPhoneNumber
                LaunchedEffect(showPhoneNumber){
                    if(showPhoneNumber) {
                        tempPhoneNumber = viewModel.phoneNumber.value
                    }
                }
                Dialog(onDismissRequest = {
                    viewModel.onDismissEditPhoneNumberDialog()
                    // При отмене - сбрасываем состояние
                    tempPhoneNumber = viewModel.phoneNumber.value
                }) {
                    Surface(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 2.dp,
                        color = Color(0xFFEADDFF)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Редактирование номера", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(16.dp))

                            // OutlinedTextFieldSimple for name
                            // Исправлено: используем tempPhoneNumber для value и убрали filter в onValueChange
                            OutlinedTextField(
                                value = tempPhoneNumber,
                                onValueChange = {
                                    tempPhoneNumber = it
                                },
                                label = { Text("Номер телефона") },
                                prefix = { Text("+7 ") },
                                placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                ),
                                singleLine = true,
                                maxLines = 1,
                                isError = tempPhoneNumber.isNotEmpty() && tempPhoneNumber.length < 10,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF65558F),
                                    unfocusedBorderColor = Color(0xFF79747E),
                                    focusedLabelColor = Color(0xFF65558F),
                                    unfocusedLabelColor = Color(0xFF79747E)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick =  {
                                    viewModel.onDismissEditPhoneNumberDialog()
                                    // При отмене - сбрасываем состояние
                                    tempPhoneNumber = viewModel.phoneNumber.value
                                }) { Text("Отменить") }
                                // Исправлено: используем isValidPhoneNumber для активации кнопки
                                Button(
                                    onClick = {
                                        // Обновляем phoneNumber в viewModel при сохранении
                                        viewModel.updatePhoneNumber(tempPhoneNumber)
                                        viewModel.onDismissEditPhoneNumberDialog()
                                    },
                                    enabled = tempPhoneNumber.length == 10
                                ) { Text("Сохранить") }
                            }
                        }
                    }
                }
            }
            // LogoutDialog
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.onDismissLogoutDialog() },
                    title = { Text("Выход из аккаунта") },
                    text = { Text("Вы уверены, что хотите выйти из аккаунта?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.logout()
                        }) {
                            Text("Да")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.onDismissLogoutDialog() }) {
                            Text("Нет")
                        }
                    }
                )
            }
        }
    }
}
    fun isValidDate(date: String): Boolean {
        val regex = """(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\d{4}""".toRegex()
        if (!regex.matches(date)) return false
        val (day, month, year) = date.split('/').map { it.toInt() }
        if (month < 1 || month > 12) return false

        val daysInMonth = when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
        return day in 1..daysInMonth
    }
}
