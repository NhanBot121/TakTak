package com.mck.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mck.composable.TopBar
import com.mck.myprofile.ui.screens.LoginScreen
import com.mck.myprofile.ui.screens.SignUpScreen
import com.mck.myprofile.ui.screens.UserProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopBar(
            navIcon = null,
            title = stringResource(id = com.mck.theme.R.string.my_account)
        )
    }) {
        NavHost(navController = navController, startDestination = "login") {
            // Define your composables here with correct navigation
            composable("login") { LoginScreen(navController = navController) }
            composable("signup") { SignUpScreen(navController = navController) }
            composable("profile") { UserProfileScreen(navController = navController) }
        }
    }
}