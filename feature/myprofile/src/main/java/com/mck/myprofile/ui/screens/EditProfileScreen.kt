package com.mck.myprofile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.mck.myprofile.user.User
import com.mck.myprofile.user.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun EditProfileScreen(
    viewModel: UserViewModel = viewModel(),
    navController: NavController
) {
    // Lấy thông tin người dùng từ Firestore
    var user by remember { mutableStateOf<User?>(null) }
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

        // Nhập URL ảnh đại diện
        OutlinedTextField(
            value = profileImageUrl,
            onValueChange = { profileImageUrl = it },
            label = { Text("Profile Image URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nhập "Following" (tùy chọn)
        OutlinedTextField(
            value = following.toString(),
            onValueChange = { following = it.toIntOrNull() ?: 0 },
            label = { Text("Following") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nhập "Followers" (tùy chọn)
        OutlinedTextField(
            value = followers.toString(),
            onValueChange = { followers = it.toIntOrNull() ?: 0 },
            label = { Text("Followers") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nhập "Likes" (tùy chọn)
        OutlinedTextField(
            value = likes.toString(),
            onValueChange = { likes = it.toIntOrNull() ?: 0 },
            label = { Text("Likes") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

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

@Preview
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = NavController(LocalContext.current))
}
