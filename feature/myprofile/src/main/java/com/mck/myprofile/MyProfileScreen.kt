package com.mck.myprofile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mck.composable.TopBar
import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.mck.core.DestinationRoute.PassedKey.USER_ID
import com.mck.core.DestinationRoute.PassedKey.VIDEO_INDEX
import com.mck.myprofile.ui.screens.LoginScreen
import com.mck.myprofile.ui.screens.SignUpScreen
import com.mck.myprofile.ui.screens.UserProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(navController: NavController) {
    Scaffold(topBar = {
        TopBar(
            navIcon = null,
            title = stringResource(id = com.mck.theme.R.string.my_account)
        )
    }) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val userId: String = "46uosK8DWVP9AijHAwjoYyE8WTV2"
            val videoIndex: Int = 1
            Button(
                onClick = {
                    Log.d("MyProfileScreen", "Navigate to Home Screen")
                    navController.navigate("$HOME_SCREEN_ROUTE/$userId/$videoIndex")
                }
            ) {
                Text("Navigate")
            }
        }
    }
}

//@Composable
//fun ProfileSCreen(){
//    val profileNavController = rememberNavController()
//    NavHost(navController = profileNavController, startDestination = "login") {
//        // Define your composables here with correct navigation
//        composable("login") {
//            LoginScreen(navController = profileNavController)
//        }
//        composable("signup") { SignUpScreen(navController = profileNavController) }
//        composable("profile") { UserProfileScreen(navController = profileNavController) }
//    }
//}