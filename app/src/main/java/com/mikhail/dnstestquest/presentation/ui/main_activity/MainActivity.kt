package com.mikhail.dnstestquest.presentation.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mikhail.dnstestquest.presentation.ui.main_activity.nav_graphs.NavRoutes
import com.mikhail.dnstestquest.presentation.ui.main_activity.nav_graphs.RootNavigationGraph
import com.mikhail.dnstestquest.presentation.ui.theme.DnsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DnsTheme {
                val navController = rememberNavController()
                RootNavigationGraph(
                    navController = navController,
                    isUserLogged = false,
                    onLogout = {
                        navController.navigate(NavRoutes.sign_in) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    }
}