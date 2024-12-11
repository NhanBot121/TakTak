package com.mck.myprofile

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mck.composable.TopBar
import com.mck.data.repository.UserRepository
import com.mck.myprofile.view.screens.EditProfileScreen
import com.mck.myprofile.view.screens.LoginScreen
import com.mck.myprofile.view.screens.SignUpScreen
import com.mck.myprofile.view.screens.UserProfileScreen
import com.mck.myprofile.viewmodel.UserFactory
import com.mck.myprofile.viewmodel.UserViewModel

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
     * NavHost of MyProfileScreen is nested in AppNavHost
     * So, need to create a new NavController*/
    val profileNavController = rememberNavController()

    //context used to implement SharedPreference in Repository, save state of log in
    val context = LocalContext.current
    val userRepository = remember{ UserRepository(context) }
    val userFactory = remember{ UserFactory(userRepository) }

    //bind viewmodel with lifecycle of activity, no need to initialize viewmodel whenever recompose
    val activity = LocalContext.current as ComponentActivity
    val userViewModel: UserViewModel = viewModel(activity, factory = userFactory)

    //observe state of log in
    val isLoggedIn = userViewModel.isLoggedIn.observeAsState(false)

    NavHost(
        navController = profileNavController,
        startDestination = if(isLoggedIn.value) "profile" else "login"
    ) {
        // Define your composables here with correct navigation
        composable("login") { LoginScreen(userViewModel, navController = profileNavController) }
        composable("signup") { SignUpScreen(userViewModel, navController = profileNavController) }
        composable("profile") { UserProfileScreen(userViewModel, navController = profileNavController, navControlMulti = navControllerMulti) }
        composable ("edit_profile") {EditProfileScreen(userViewModel, navController = profileNavController)}
    }
}

