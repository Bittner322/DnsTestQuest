package com.mikhail.dnstestquest.presentation.ui.main_activity.nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mikhail.dnstestquest.presentation.ui.screens.create_task.CreateTaskScreen
import com.mikhail.dnstestquest.presentation.ui.screens.home.HomeScreen
import com.mikhail.dnstestquest.presentation.ui.screens.sign_in.SignInScreen
import com.mikhail.dnstestquest.presentation.ui.screens.task_detalisation.TaskDetalizationScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    isUserLogged: Boolean,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserLogged) NavRoutes.home else NavRoutes.sign_in
    ) {
        composable(route = NavRoutes.sign_in) {
            SignInScreen(navController = navController)
        }
        composable(route = NavRoutes.home) {
            HomeScreen(
                navController = navController,
                onLogout = onLogout
            )
        }
        composable(route = NavRoutes.create_task) {
            CreateTaskScreen(navController = navController)
        }
        composable(route = "${NavRoutes.task}/{taskId}") {
            TaskDetalizationScreen(navController = navController)
        }
    }
}