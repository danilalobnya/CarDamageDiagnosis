package com.example.car_damage_diagnosis.ui.navigation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.car_damage_diagnosis.ui.screens.history.HistoryActivity
import com.example.car_damage_diagnosis.ui.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome
    ) {
        composable(Screen.Welcome) {
            WelcomeScreen(
                onNavigateToHome = {
                    val activity = (context as? Activity)
                    val intent = Intent(context, HistoryActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                }

            )
        }

        composable(Screen.CameraCapture) {
            // TODO: Implement CameraCaptureScreen
        }

        composable(Screen.DamageAnalysis) {
            // TODO: Implement DamageAnalysisScreen
        }

        composable(Screen.Profile) {
            // TODO: Implement ProfileScreen
        }

        composable(Screen.Settings) {
            // TODO: Implement SettingsScreen
        }
    }
}