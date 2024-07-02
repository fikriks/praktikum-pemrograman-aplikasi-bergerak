package com.example.absen.Utils

import com.example.absen.navigation.Screen

fun String?.shouldShowBottomBar(): Boolean {
    return this in setOf(
        Screen.Absen.route,
        Screen.Jurnal.route,
        Screen.Profile.route,


        )
}