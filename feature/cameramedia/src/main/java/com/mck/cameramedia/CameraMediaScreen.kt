package com.mck.cameramedia

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mck.cameramedia.tabs.CameraScreen
import com.mck.cameramedia.tabs.TemplateScreen
import com.mck.core.extension.getCurrentBrightness
import com.mck.core.utils.DisableRippleInteractionSource
import com.mck.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CameraMediaScreen(
    navController: NavController,
    cameraMediaViewModel: CameraMediaViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = Tabs.entries
    val pagerState = rememberPagerState(pageCount = { 3 }) // Correct pager state
    val context = LocalContext.current
    val minimumScreenBrightness = 0.25f

    // Handle screen brightness
    DisposableEffect(key1 = Unit) {
        val attrs = (context as Activity).window.attributes
        if (context.getCurrentBrightness() < minimumScreenBrightness) {
            attrs.screenBrightness = minimumScreenBrightness
            context.window.attributes = attrs
        }
        onDispose {
            attrs.screenBrightness = context.getCurrentBrightness()
            context.window.attributes = attrs
        }
    }

    // Scaffold layout with pager and bottom tab
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Pager to display different screens
            Box(modifier = Modifier.weight(1f)) {
//                HorizontalPager(
//                    state = pagerState,
//                    modifier = Modifier.fillMaxSize(),
//                    userScrollEnabled = false
//                ) { page ->
//                    when (page) {
//                        0, 1 -> CameraScreen(
//                            navController = navController,
//                            viewModel = cameraMediaViewModel,
//                            cameraOpenType = tabs[page]
//                        )
//                        2 -> TemplateScreen(
//                            navController = navController,
//                            viewModel = cameraMediaViewModel
//                        )
//                    }
//                }

                CameraScreen(
                    navController = navController,
                    viewModel = cameraMediaViewModel,
                    cameraOpenType = tabs[1]
                )

            }
            // Bottom tab layout for navigation
            BottomTabLayout(pagerState) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomTabLayout(
    pagerState: PagerState,
    onClickTab: (position: Int) -> Unit
) {
    val edgePadding = LocalConfiguration.current.screenWidthDp.div(2).dp
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {},
        indicator = {},
        edgePadding = edgePadding
    ) {
        Tabs.entries.forEachIndexed { index, tab ->
            val isSelected = pagerState.currentPage == index
            Tab(
                selected = isSelected,
                onClick = {
                    onClickTab(index)
                },
                interactionSource = remember { DisableRippleInteractionSource() },
                text = {
                    val textColor = if (isSelected) White else White.copy(alpha = 0.6f)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = tab.rawValue),
                            style = MaterialTheme.typography.labelLarge,
                            color = textColor
                        )
                        Box(
                            modifier = Modifier
                                .alpha(if (isSelected) 1f else 0f)
                                .padding(top = 10.dp)
                                .size(5.dp)
                                .background(color = White, shape = CircleShape)
                        )
                    }
                }
            )
        }
    }
}
