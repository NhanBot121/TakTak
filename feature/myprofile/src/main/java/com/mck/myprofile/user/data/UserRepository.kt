package com.mck.myprofile.user.data
import android.content.Context
import android.content.SharedPreferences
import com.mck.myprofile.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val _context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _sharedPreference: SharedPreferences = _context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedin: Boolean){
        _sharedPreference.edit().putBoolean("isLoggedin", isLoggedin).apply()
    }
    fun isUserLoggedin() : Boolean{
        return _sharedPreference.getBoolean("isLoggedIn", false)
    }

    // Đăng ký người dùng
    suspend fun registerUser(email: String, password: String): Boolean {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                val user = User(id = it.uid, email = email)
                saveUserToFirestore(user)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    // Đăng nhập người dùng
    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user != null
        } catch (e: Exception) {
            false
        }
    }

    // Lưu thông tin người dùng vào Firestore
    suspend fun saveUserToFirestore(user: User): Boolean {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Lấy thông tin người dùng từ Firestore
    suspend fun getUserFromFirestore(userId: String): User? {
        return try {
            val doc = firestore.collection("users").document(userId).get().await()
            doc.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
}