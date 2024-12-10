package com.mck.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mck.composable.TopBar
import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.mck.core.DestinationRoute.PassedKey.USER_ID
import com.mck.core.DestinationRoute.PassedKey.VIDEO_INDEX
import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE
import com.mck.myprofile.ui.screens.EditProfileScreen
import com.mck.myprofile.ui.screens.LoginScreen
import com.mck.myprofile.ui.screens.SignUpScreen
import com.mck.myprofile.ui.screens.UserProfileScreen
import com.mck.myprofile.user.UserFactory
import com.mck.myprofile.user.UserViewModel
import com.mck.myprofile.user.data.UserRepository

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyProfileScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopBar(
            navIcon = null,
            title = stringResource(id = com.mck.theme.R.string.my_account)
        )
    }) {
        ProfileScreen(navController)
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ProfileScreen(navControllerMulti: NavHostController){
    /**
     * NavHost of */
    val profileNavController = rememberNavController()

    val activity = LocalContext.current as ComponentActivity
    val context = LocalContext.current
    val userRepository = remember{ UserRepository(context) }
    val userFactory = remember{ UserFactory(userRepository) }

    val userViewModel: UserViewModel = viewModel(activity, factory = userFactory)
    val isLoggedIn = userViewModel.isLoggedIn.observeAsState(false)

    NavHost(
        navController = profileNavController,
        startDestination = if(isLoggedIn.value) "profile" else "login"
    ) {
        // Define your composables here with correct navigation
        composable("login") { LoginScreen(userViewModel, navController = profileNavController) }
        composable("signup") { SignUpScreen(userViewModel, navController = profileNavController) }
        composable("profile") { UserProfileScreen(userViewModel, navController = profileNavController, navControlMulti = navControllerMulti) }
        composable (route = "edit_profile") {EditProfileScreen(userViewModel, navController = profileNavController)}
    }
}

