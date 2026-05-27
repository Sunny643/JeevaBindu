package com.jeevabindu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeevabindu.app.navigation.NavGraph
import com.jeevabindu.app.navigation.Routes
import com.jeevabindu.app.ui.components.BottomNavBar
import com.jeevabindu.app.ui.theme.JeevaBinduTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JeevaBinduTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Screens that show the bottom nav
                val showBottomNav = currentRoute in listOf(
                    Routes.DASHBOARD,
                    Routes.DIRECTORY,
                    Routes.FEED,
                    Routes.SETTINGS
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomNav) {
                            BottomNavBar(
                                currentRoute = currentRoute ?: Routes.DASHBOARD,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo(Routes.DASHBOARD) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}
