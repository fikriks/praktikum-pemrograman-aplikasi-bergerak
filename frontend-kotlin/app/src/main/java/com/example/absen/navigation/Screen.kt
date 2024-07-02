package com.example.absen.navigation

sealed class Screen(val route : String){
    data object Login : Screen("login")
    data object Absen : Screen("absen")
    data object Jurnal : Screen("jurnal")
    data object Profile : Screen("Profile")
}