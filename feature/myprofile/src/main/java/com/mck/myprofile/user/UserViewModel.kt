package com.mck.myprofile.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mck.myprofile.user.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Đăng ký người dùng
    fun registerUser(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = userRepository.registerUser(email, password)
                if (success) {
                    onResult(true, "Registration successful")
                } else {
                    onResult(false, "Registration failed")
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Unknown error")
            }
        }
    }

    // Đăng nhập người dùng
    fun loginUser(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = userRepository.loginUser(email, password)
                if (success) {
                    onResult(true, "Login successful")
                } else {
                    onResult(false, "Login failed")
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Unknown error")
            }
        }
    }

    // Lấy thông tin người dùng từ Firestore
    fun getUserInfo(onResult: (User?) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                try {
                    val user = userRepository.getUserFromFirestore(userId)
                    onResult(user)
                } catch (e: Exception) {
                    onResult(null)
                }
            }
        } else {
            onResult(null) // Nếu người dùng chưa đăng nhập, trả về null
        }
    }

    // Cập nhật thông tin người dùng
    // UserViewModel
    // UserViewModel
    fun updateUserProfile(
        name: String,
        email: String,
        profileImageUrl: String,
        following: Int,
        followers: Int,
        likes: Int,
        onResult: (Boolean) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                try {
                    // Tạo hashMap với tất cả các trường dữ liệu mới
                    val userUpdates = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "profileImageUrl" to profileImageUrl,
                        "following" to following,
                        "followers" to followers,
                        "likes" to likes
                    )

                    // Cập nhật thông tin người dùng vào Firestore
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(userId)
                        .update(userUpdates as Map<String, Any>)
                        .await() // Chờ cho đến khi hoàn thành

                    onResult(true)
                } catch (e: Exception) {
                    // In log lỗi để kiểm tra
                    println("Update user profile failed: ${e.message}")
                    onResult(false)
                }
            }
        } else {
            onResult(false) // Nếu không có userId, trả về false
        }
    }


    // Đăng xuất người dùng
    fun logout() {
        viewModelScope.launch {
            try {
                firebaseAuth.signOut()
            } catch (e: Exception) {
                println("Logout Error: ${e.message}")
            }
        }
    }
}
