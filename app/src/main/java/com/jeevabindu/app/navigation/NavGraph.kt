package com.jeevabindu.app.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jeevabindu.app.ui.screens.alert.BloodAlertScreen
import com.jeevabindu.app.ui.screens.dashboard.DonorDashboardScreen
import com.jeevabindu.app.ui.screens.directory.LiveDirectoryScreen
import com.jeevabindu.app.ui.screens.emergency.CreateEmergencyPostScreen
import com.jeevabindu.app.ui.screens.emergency.EmergencyDetailsScreen
import com.jeevabindu.app.ui.screens.feed.CommunityFeedScreen
import com.jeevabindu.app.ui.screens.health.HealthTrackerScreen
import com.jeevabindu.app.ui.screens.profile.DonorProfileSetupScreen
import com.jeevabindu.app.ui.screens.route.OnRouteStatusScreen
import com.jeevabindu.app.ui.screens.settings.SettingsPrivacyScreen
import com.jeevabindu.app.ui.screens.splash.SplashScreen
import com.jeevabindu.app.ui.screens.verification.PhoneVerificationScreen
import com.jeevabindu.app.viewmodel.DashboardViewModel
import com.jeevabindu.app.viewmodel.DirectoryViewModel
import com.jeevabindu.app.viewmodel.EmergencyViewModel
import com.jeevabindu.app.viewmodel.SettingsViewModel

object Routes {
    const val SPLASH = "splash"
    const val PHONE_VERIFICATION = "phone_verification"
    const val PROFILE_SETUP = "profile_setup"
    const val DASHBOARD = "dashboard"
    const val DIRECTORY = "directory"
    const val FEED = "feed"
    const val SETTINGS = "settings"
    const val BLOOD_ALERT = "blood_alert"
    const val EMERGENCY_DETAILS = "emergency_details"
    const val CREATE_EMERGENCY = "create_emergency"
    const val HEALTH_TRACKER = "health_tracker"
    const val ON_ROUTE = "on_route"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = viewModel(),
    directoryViewModel: DirectoryViewModel = viewModel(),
    emergencyViewModel: EmergencyViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val currentUser by dashboardViewModel.currentUser.collectAsStateWithLifecycle()
    val donationHistory by dashboardViewModel.donationHistory.collectAsStateWithLifecycle()
    val filteredDonors by directoryViewModel.filteredDonors.collectAsStateWithLifecycle()
    val selectedBloodGroup by directoryViewModel.selectedBloodGroup.collectAsStateWithLifecycle()
    val proximityKm by directoryViewModel.proximityKm.collectAsStateWithLifecycle()
    val showAlert by emergencyViewModel.showAlert.collectAsStateWithLifecycle()
    val currentAlert by emergencyViewModel.currentAlert.collectAsStateWithLifecycle()
    val isEnRoute by emergencyViewModel.isEnRoute.collectAsStateWithLifecycle()
    val routeProgress by emergencyViewModel.routeProgress.collectAsStateWithLifecycle()
    val emergencyNotifications by settingsViewModel.emergencyNotifications.collectAsStateWithLifecycle()
    val locationSharing by settingsViewModel.locationSharing.collectAsStateWithLifecycle()

    // Show blood alert overlay
    if (showAlert && currentAlert != null) {
        BloodAlertScreen(
            emergency = currentAlert,
            onViewDetails = {
                emergencyViewModel.dismissAlert()
                navController.navigate(Routes.EMERGENCY_DETAILS)
            },
            onDismiss = { emergencyViewModel.dismissAlert() }
        )
        return
    }

    // Show en-route screen
    if (isEnRoute) {
        OnRouteStatusScreen(
            progress = routeProgress,
            onArrived = {
                emergencyViewModel.markArrived()
                navController.navigate(Routes.DASHBOARD) { popUpTo(Routes.DASHBOARD) { inclusive = true } }
            },
            onCancel = {
                emergencyViewModel.cancelRoute()
                navController.navigate(Routes.DASHBOARD) { popUpTo(Routes.DASHBOARD) { inclusive = true } }
            },
            onCallHospital = {}
        )
        return
    }

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onEnterCommunity = { navController.navigate(Routes.PHONE_VERIFICATION) { popUpTo(Routes.SPLASH) { inclusive = true } } }
            )
        }

        composable(Routes.PHONE_VERIFICATION) {
            PhoneVerificationScreen(
                onVerified = { navController.navigate(Routes.PROFILE_SETUP) { popUpTo(Routes.PHONE_VERIFICATION) { inclusive = true } } }
            )
        }

        composable(Routes.PROFILE_SETUP) {
            DonorProfileSetupScreen(
                onProfileComplete = { navController.navigate(Routes.DASHBOARD) { popUpTo(Routes.PROFILE_SETUP) { inclusive = true } } }
            )
        }

        composable(Routes.DASHBOARD) {
            DonorDashboardScreen(
                currentUser = currentUser,
                donationHistory = donationHistory,
                onNavigateDirectory = { navController.navigate(Routes.DIRECTORY) },
                onNavigateHealth = { navController.navigate(Routes.HEALTH_TRACKER) },
                onTriggerAlert = { emergencyViewModel.triggerSampleAlert() }
            )
        }

        composable(Routes.DIRECTORY) {
            LiveDirectoryScreen(
                donors = filteredDonors,
                selectedBloodGroup = selectedBloodGroup,
                proximityKm = proximityKm,
                onBloodGroupSelected = { directoryViewModel.setBloodGroupFilter(it) },
                onProximityChanged = { directoryViewModel.setProximity(it) }
            )
        }

        composable(Routes.FEED) {
            CommunityFeedScreen(
                onCreateEmergency = { navController.navigate(Routes.CREATE_EMERGENCY) }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsPrivacyScreen(
                emergencyNotifications = emergencyNotifications,
                locationSharing = locationSharing,
                onToggleEmergency = { settingsViewModel.toggleEmergencyNotifications() },
                onToggleLocation = { settingsViewModel.toggleLocationSharing() },
                onLogout = { navController.navigate(Routes.SPLASH) { popUpTo(0) { inclusive = true } } }
            )
        }

        composable(Routes.EMERGENCY_DETAILS) {
            EmergencyDetailsScreen(
                emergency = currentAlert,
                onImComing = {
                    currentAlert?.let { emergencyViewModel.respondToEmergency(it.id) }
                },
                onCallHospital = {},
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CREATE_EMERGENCY) {
            CreateEmergencyPostScreen(
                onPost = { bg, hospital, urgency, contact, units ->
                    emergencyViewModel.createEmergencyPost(bg, hospital, urgency, contact, units)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HEALTH_TRACKER) {
            HealthTrackerScreen(
                currentUser = currentUser,
                donationHistory = donationHistory
            )
        }

        composable(Routes.ON_ROUTE) {
            OnRouteStatusScreen(
                progress = routeProgress,
                onArrived = { emergencyViewModel.markArrived() },
                onCancel = { emergencyViewModel.cancelRoute() },
                onCallHospital = {}
            )
        }
    }
}
