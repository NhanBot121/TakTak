package com.mck.myprofile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.mck.myprofile.user.User
import com.mck.myprofile.user.UserViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(viewModel: UserViewModel = viewModel(), navController: NavHostController) {
    // Lấy thông tin người dùng từ Firebase
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Lấy thông tin người dùng từ Firestore
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
                    // Thêm biểu tượng nếu cần
                    IconButton(onClick = { /* Handle settings */ }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
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
                        Button(
                            onClick = {
                                // Điều hướng tới màn hình chỉnh sửa hồ sơ
                                navController.navigate("editProfile")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Edit Profile")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Nút Đăng xuất
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
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(it.videoThumbnailUrls) { thumbnailUrl ->
                                    // Ảnh thumbnail
                                    if (thumbnailUrl.isNotEmpty()) {
                                        Image(
                                            painter = rememberImagePainter(thumbnailUrl),
                                            contentDescription = "Video Thumbnail",
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape)
                                                .border(2.dp, Color.Gray, CircleShape)
                                                .clickable {
                                                    // Bạn có thể thêm logic để điều hướng hoặc phát video khi nhấn vào thumbnail
                                                }
                                        )
                                    }
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

@Preview
@Composable
fun UserProfileScreenPreview() {
    UserProfileScreen(navController = NavHostController(LocalContext.current))
}