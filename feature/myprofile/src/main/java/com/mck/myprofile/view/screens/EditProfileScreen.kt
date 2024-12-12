package com.mck.myprofile.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mck.data.model.UserModel
import com.mck.myprofile.viewmodel.UserViewModel
import coil.compose.rememberImagePainter

@Composable
fun EditProfileScreen(
    viewModel: UserViewModel,
    navController: NavHostController
) {
    // Lấy thông tin người dùng từ Firestore
    var user by remember { mutableStateOf<UserModel?>(null) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    var following by remember { mutableStateOf(0) } // Dữ liệu mặc định cho "following"
    var followers by remember { mutableStateOf(0) } // Dữ liệu mặc định cho "followers"
    var likes by remember { mutableStateOf(0) } // Dữ liệu mặc định cho "likes"
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Lấy thông tin người dùng khi màn hình được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserInfo { fetchedUser ->
            if (fetchedUser != null) {
                user = fetchedUser
                name = fetchedUser.name
                email = fetchedUser.email
                profileImageUrl = fetchedUser.profileImageUrl
                following = fetchedUser.following
                followers = fetchedUser.followers
                likes = fetchedUser.likes
            } else {
                errorMessage = "Error fetching user data"
            }
        }
    }

    // Giao diện chỉnh sửa thông tin người dùng
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hiển thị thông báo lỗi (nếu có)
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Nhập tên
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nhập email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị ảnh đại diện hiện tại nếu có
        if (user?.profileImageUrl?.isNotEmpty() == true) {
            val imagePainter = rememberImagePainter(user?.profileImageUrl)
            Image(
                painter = imagePainter,
                contentDescription = "Profile Image",
                modifier = Modifier.size(120.dp)
            )
        }

        // Nút lưu thông tin
        Button(
            onClick = {
                isLoading = true
                viewModel.updateUserProfile(name, email, profileImageUrl, following, followers, likes) { success ->
                    isLoading = false
                    if (success) {
                        navController.popBackStack()  // Quay lại màn hình trước đó
                    } else {
                        errorMessage = "Cập nhật thất bại"
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text("Save Changes")
        }

        // Chế độ tải
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
