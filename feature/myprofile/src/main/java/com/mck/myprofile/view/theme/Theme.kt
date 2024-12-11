package com.mck.myprofile.view.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme

//import com.example.profile.ui.components.UserForm

// Màu sắc cho theme sáng
private val LightColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFFf5f5f5),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

// Màu sắc cho theme tối
private val DarkColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White,
)

// Áp dụng theme cho ứng dụng của bạn
@Composable
fun TiktokAppTheme(
    darkTheme: Boolean = false, // Bạn có thể cho phép người dùng chọn chế độ tối hoặc sáng
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

//@Composable
//@Preview(showBackground = true)
//fun DefaultPreview() {
//    TiktokAppTheme {
//        // Preview nội dung khi sử dụng theme
//        // Ví dụ có thể cho UserForm vào đây
//        UserForm(navController = NavController, isLogin = true)
//    }
//}
