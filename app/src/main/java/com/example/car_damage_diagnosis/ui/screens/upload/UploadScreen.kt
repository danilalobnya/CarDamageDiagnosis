package com.example.car_damage_diagnosis.ui.screens.upload

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.car_damage_diagnosis.R
import com.example.car_damage_diagnosis.ui.screens.loader.LoaderScreen
import java.io.File
import java.io.IOException

class UploadScreen : ComponentActivity() {

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private var photoUris = mutableStateListOf<Uri>()
    private var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация ActivityResultLaunchers
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                currentPhotoPath?.let {
                    photoUris.add(Uri.parse(it))
                    Log.d("UploadScreen", "Image URI from camera: ${photoUris.last()}")
                }
            } else {
                Log.d("UploadScreen", "Camera operation failed.")
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                if (photoUris.size < 5) {
                    photoUris.add(it)
                    Log.d("UploadScreen", "Image URI from gallery: $it")
                }
            }
        }

        setContent {
            PhotoUploadScreen(
                photoUris = photoUris,
                onTakePhotoClick = { requestCameraPermission() },
                onGalleryClick = { openGallery() },
                onDeletePhotoClick = { photoUris.removeLastOrNull() }
            )
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            openCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

    private fun openCamera() {
        try {
            val photoFile: File = createImageFile()
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.car_damage_diagnosis.fileprovider",
                photoFile
            )
            currentPhotoPath = photoFile.absolutePath

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            cameraLauncher.launch(intent)
        } catch (ex: IOException) {
            Log.e("UploadScreen", "Error creating image file: $ex")
        }
    }

    private fun createImageFile(): File {
        val storageDir: File = getExternalFilesDir(null) ?: filesDir
        return File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_", // Prefix
            ".jpg", // Suffix
            storageDir // Directory
        )
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    @Composable
    fun PhotoUploadScreen(
        photoUris: List<Uri>,
        onTakePhotoClick: () -> Unit,
        onGalleryClick: () -> Unit,
        onDeletePhotoClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Загрузка фотографий",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .horizontalScroll(rememberScrollState()), // Это позволит прокручивать фотографии
                contentAlignment = Alignment.Center
            ) {
                if (photoUris.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        photoUris.forEachIndexed { index, uri ->
                            Image(
                                painter = rememberAsyncImagePainter(model = uri),
                                contentDescription = "Фото ${index + 1}",
                                modifier = Modifier
                                    .fillMaxSize() // Растягиваем изображение на весь доступный размер
                                    .aspectRatio(1f) // Сохраняем соотношение сторон (если нужно)
                                    .padding(end = 8.dp) // Добавляем отступы между изображениями
                            )
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image),
                            contentDescription = "Пустое фото",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "Фотографии отсутствуют",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Описание
            Text(
                text = "Загрузите до 5 фотографий",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

// Кнопки управления
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Кнопка камеры
                Button(
                    onClick = onTakePhotoClick, // Логика для съемки
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDE7F6))
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Камера",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Кнопка галереи
                Button(
                    onClick = onGalleryClick, // Логика для выбора фото из галереи
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDE7F6))
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Галерея",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Кнопка удаления всех фотографий
                Button(
                    onClick = onDeletePhotoClick, // Используем переданную логику для удаления
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDE7F6))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = Color.Black
                    )
                }
            }

// Кнопка "Анализ"
            val context = LocalContext.current

            Button(
                onClick = {
                    val intent = Intent(context, LoaderScreen::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = photoUris.isNotEmpty()
            ) {
                Text(text = "Анализ")
            }
        }
    }
}