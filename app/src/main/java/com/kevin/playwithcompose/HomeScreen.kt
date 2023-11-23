package com.kevin.playwithcompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kevin.composestudy.bean.BannerData
import com.kevin.playwithcompose.util.LogUtils.printD
import com.kevin.playwithcompose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val image1 =
        "https://img1.baidu.com/it/u=1546227440,2897989905&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"
    val image2 = "https://lmg.jj20.com/up/allimg/1114/0406210Z024/2104060Z024-5-1200.jpg"
    val image3 = "https://lmg.jj20.com/up/allimg/1111/04131Q42551/1P413142551-4-1200.jpg"
    val image4 =
        "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/500fd9f9d72a6059b4256d352e34349b033bbaec.jpg"
    val viewModel: HomeViewModel = viewModel()
    val pagerList: List<BannerData> by viewModel.bannerData
//    val pagerList = listOf(image4, image1, image2, image3, image4, image1)
    Scaffold(topBar = { AppBar(title = "Home") }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            printD("value=======${pagerList}")
            val pagerState = rememberPagerState(
                initialPage = 1,
                initialPageOffsetFraction = 0f
            ) {
                pagerList.size
            }
            LaunchedEffect(key1 = pagerState) {
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
            val coroutineScope = rememberCoroutineScope()
            DisposableEffect(key1 = pagerState) {
                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }, 3000, 3000)
                onDispose {
                    timer.cancel()
                }

            }
            HorizontalPager(state = pagerState, beyondBoundsPageCount = 1) { index ->
                Card(modifier = Modifier
                    .padding(horizontal = 10.dp)
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
    }

}