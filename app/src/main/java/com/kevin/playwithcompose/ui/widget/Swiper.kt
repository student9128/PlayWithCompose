package com.kevin.playwithcompose.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kevin.composestudy.bean.BannerData
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Swiper(list: List<BannerData>,modifier: Modifier=Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val pagerList = rememberUpdatedState(newValue = list).value
    val pagerState = rememberPagerState(
        initialPage = 1,
        initialPageOffsetFraction = 0f
    ) {
        pagerList.size
    }
    LaunchedEffect(key1 = pagerState, key2 = list) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    if (pagerState.currentPage == pagerList.size - 1) pagerState.scrollToPage(
                        1
                    ) else if (pagerState.currentPage == 0) pagerState.scrollToPage(
                        pagerList.size - 2
                    )
                }
            }

    }
    DisposableEffect(key1 = pagerState) {
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
            timer.schedule(task, 3000, 3000)
        onDispose {
            task.cancel()
            timer.cancel()
        }

    }
    HorizontalPager(state = pagerState, beyondBoundsPageCount = 1, modifier = modifier) { index ->
        Card(modifier = Modifier
//            .padding(horizontal = 10.dp)
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - index) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue
                val scale = lerp(
                    start = ScaleFactor(0.8f, 0.8f),
                    stop = ScaleFactor(1f, 1f),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                scaleX = scale.scaleX
                scaleY = scale.scaleY
            }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pagerList[index].imagePath)
                    .crossfade(true)
                    .build(),
//                model = image4,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}