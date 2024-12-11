package com.mck.myprofile.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mck.data.model.UserModel
import com.mck.myprofile.viewmodel.UserViewModel
import androidx.navigation.NavHostController
import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(viewModel: UserViewModel, navController: NavHostController, navControlMulti: NavHostController) {
    // get user info from Firebase
    var user by remember { mutableStateOf<UserModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // get user info from Firebase
    LaunchedEffect(Unit) {
        viewModel.getUserInfo { fetchedUser ->
            if (fetchedUser != null) {
                user = fetchedUser
            } else {
                errorMessage = "Error fetching user data"
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            // Toolbar
            TopAppBar(
                title = {
                    Text(text = "User Profile", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        navController.navigate("login") { popUpTo("login") { inclusive = true } }
                    }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Log Out")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    user?.let {
                        // Ảnh đại diện với viền tròn
                        val imagePainter = rememberImagePainter(it.profileImageUrl)
                        if (it.profileImageUrl.isNotEmpty()) {
                            Image(
                                painter = imagePainter,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape) // Viền xung quanh
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape) // Viền cho icon mặc định
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Thông tin người dùng
                        Text(text = "Name: ${it.name}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Email: ${it.email}", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Hiển thị Followers, Following, Likes ngang hàng với nhau
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${it.followers}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Followers", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${it.following}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Following", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${it.likes}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Likes", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Nút Chỉnh sửa hồ sơ
                        OutlinedButton(
                            onClick = {
                                // Điều hướng tới màn hình chỉnh sửa hồ sơ
                                navController.navigate("edit_profile")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(50.dp), // Điều chỉnh chiều cao để giống hình ảnh
                            shape = MaterialTheme.shapes.medium, // Góc bo tròn
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Edit Profile")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // sign out to login screen
                        Button(
                            onClick = {
                                viewModel.logout()
                                navController.navigate("login") { popUpTo("login") { inclusive = true } }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Log Out")
                        }


                        // Hiển thị video thumbnails
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Your Favorite Videos", style = MaterialTheme.typography.titleMedium)

                        // LazyRow để hiển thị các video thumbnail
                        if (it.videoThumbnailUrls.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3), // 3 video mỗi hàng
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(it.videoThumbnailUrls.size) { thumbnailUrl ->
                                    // Ảnh thumbnail
                                    //if (thumbnailUrl.isNotEmpty()) {
                                    Image(
                                        painter = rememberImagePainter(it.videoThumbnailUrls[thumbnailUrl]),
                                        contentDescription = "Video Thumbnail",
                                        modifier = Modifier
                                            .size(120.dp)
                                            .border(1.dp, Color.Gray)
                                            .clickable {
                                                Log.d("MyProfileScreen", "Navigate to Home Screen")
                                                navControlMulti.navigate("$HOME_SCREEN_ROUTE/${it.id}/$thumbnailUrl")
                                            }
                                    )
                                }
                            }
                        } else {
                            Text(text = "No videos found", style = MaterialTheme.typography.bodySmall)
                        }

                    }
                    // Hiển thị thông báo lỗi (nếu có)
                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    }

                    // Phần Video để trống, bạn có thể thêm sau
                    Spacer(modifier = Modifier.height(200.dp))  // Khoảng trống cho video
                }
            }
        }
    )
}
