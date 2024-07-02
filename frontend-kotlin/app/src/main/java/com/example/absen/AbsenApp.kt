package com.example.absen

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.absen.Utils.shouldShowBottomBar
import com.example.absen.ViewModel.AuthViewModel
import com.example.absen.navigation.NavigationItem
import com.example.absen.navigation.Screen
import com.example.absen.presentation.screen.AbsenScreen
import com.example.absen.presentation.screen.JurnalScreen
import com.example.absen.presentation.screen.LoginScreen
import com.example.absen.presentation.screen.ProfileScreen

@Composable
fun AbsenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStack?.destination?.route
    val context = LocalContext.current
    val isUserLoggedIn = authViewModel.auth.currentUser

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute.shouldShowBottomBar() && isUserLoggedIn != null
            ) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn != null) Screen.Absen.route else Screen.Login.route,
            modifier = modifier.padding(contentPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navController, authViewModel)
            }
            composable(Screen.Absen.route) {
                AbsenScreen(activity = context as ComponentActivity)
            }
            composable(Screen.Jurnal.route) {
                JurnalScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController, authViewModel = authViewModel)
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xff043C5B)
) {
    NavigationBar(modifier = modifier.height(50.dp), containerColor = backgroundColor) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Absen",
                icon = R.drawable.home,
                iconSelected = R.drawable.home1,
                screen = Screen.Absen
            ),
            NavigationItem(
                title = "Jurnal",
                icon = R.drawable.openbook,
                iconSelected = R.drawable.openbook1,
                screen = Screen.Jurnal
            ),
            NavigationItem(
                title = "Profile",
                icon = R.drawable.user1,
                iconSelected = R.drawable.user,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    val iconRes = if (currentRoute == item.screen.route) item.iconSelected else item.icon
                    Icon(
                        painterResource(id = iconRes),
                        contentDescription = item.title,
                        modifier.size(30.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Unspecified,
                    unselectedIconColor = Color.Unspecified,
                    selectedTextColor = Color.Unspecified,
                    unselectedTextColor = Color.Unspecified,
                    indicatorColor = Color(0xff043C5B)
                )
            )
        }
    }
}