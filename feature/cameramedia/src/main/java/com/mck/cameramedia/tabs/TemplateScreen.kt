package com.mck.cameramedia.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mck.cameramedia.CameraMediaEvent
import com.mck.cameramedia.CameraMediaViewModel
import com.mck.composable.CustomButton
import com.mck.core.extension.LargeSpace
import com.mck.core.extension.MediumSpace
import com.mck.core.extension.SmallSpace
import com.mck.core.extension.Space
import com.mck.data.model.TemplateModel
import com.mck.theme.R
import com.mck.theme.SubTextColor
import kotlin.math.absoluteValue


@Composable
fun TemplateScreen(
    navController: NavController,
    viewModel: CameraMediaViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(CameraMediaEvent.EventFetchTemplate)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 20.dp, start = 6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            viewState?.templates?.let {
                TemplatePager(it)
            }
        }
        CustomButton(
            buttonText = stringResource(id = R.string.upload_photos),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {

        }
//        LargeSpace()
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TemplatePager(templates: List<TemplateModel>) {
    val pagerState = rememberPagerState( pageCount = { templates.size })

    val currentItem by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    Text(
        text = templates[currentItem].name,
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = templates[currentItem].hint,
        style = MaterialTheme.typography.labelLarge,
        color = SubTextColor
    )
    Spacer(modifier = Modifier.height(16.dp))
//    HorizontalPager(
//        state = pagerState,
//        contentPadding = PaddingValues(horizontal = 64.dp),
//        modifier = Modifier.weight(1f)
//    ) { page ->
//        SingleTemplateCard(page = page, pagerState = pagerState, item = templates[page])
//    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "${currentItem.plus(1)}/${templates.size}",
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleTemplateCard(
    page: Int,
    pagerState: PagerState,
    item: TemplateModel,
) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.82f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            Modifier
                .blur(if (pagerState.settledPage == page) 0.dp else 60.dp)
        )
        {
            AsyncImage(
                model = item.parseMediaLink(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}