package com.kevin.playwithcompose

import android.content.Context
import android.text.Html
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kevin.composestudy.bean.BannerData
import com.kevin.playwithcompose.bean.ArticleListData
import com.kevin.playwithcompose.ui.theme.Pink80
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.primaryLight
import com.kevin.playwithcompose.ui.theme.tertiary
import com.kevin.playwithcompose.util.LogUtils.printD
import com.kevin.playwithcompose.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.math.absoluteValue

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun HomeScreen(context: Context = LocalContext.current.applicationContext) {
    val image1 =
        "https://img1.baidu.com/it/u=1546227440,2897989905&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"
    val image2 = "https://lmg.jj20.com/up/allimg/1114/0406210Z024/2104060Z024-5-1200.jpg"
    val image3 = "https://lmg.jj20.com/up/allimg/1111/04131Q42551/1P413142551-4-1200.jpg"
    val image4 =
        "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/500fd9f9d72a6059b4256d352e34349b033bbaec.jpg"
    val viewModel: HomeViewModel = viewModel()
    val pagerList: List<BannerData> by viewModel.bannerData
    val articleList: ArticleListData? by viewModel.articleData
//    val pagerList = listOf(image4, image1, image2, image3, image4, image1)
    val listState = rememberLazyListState()
    val endOfListReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd(2)
        }
    }
    val showScrollToTop by remember {
        printD("first===${listState.firstVisibleItemIndex}")
        derivedStateOf { listState.firstVisibleItemIndex > 10 }
    }
    val visible by remember {
        mutableStateOf(showScrollToTop)
    }
    printD("first===$showScrollToTop")
    val coroutineScope = rememberCoroutineScope()

    // act when end of list reached
    LaunchedEffect(endOfListReached) {
        // do your stuff
        viewModel.requestArticleList(viewModel.currentNum + 1)
    }
    Scaffold(topBar = { AppBar(title = "Home") }) { innerPadding ->
        Box {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            ) {
                item {
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

                items(
                    count = articleList?.datas?.size ?: 0,
                    contentType = { index -> articleList?.datas?.get(index) }) { index ->
                    val articleData = articleList!!.datas[index]
                    val decodedText =
                        Html.fromHtml(articleData.title, Html.FROM_HTML_MODE_LEGACY).toString()
                    Card(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(CornerSize(10.dp)))
                            .clickable() {
                                Toast
                                    .makeText(context, "$index", Toast.LENGTH_SHORT)
                                    .show()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (articleData.author.isEmpty()) Color(0xffEBF3E8) else Color(
                                0xffF4EEEE
                            )
                        )
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {

                            Text(
                                text = decodedText,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            FlowRow(modifier = Modifier.padding(vertical = 10.dp)) {
                                articleData.tags.forEach { item ->
                                    Text(
                                        text = item.name,
                                        color = primaryLight,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp,
                                                color = primaryLight,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row {
                                    Text(
                                        text = articleData.author.ifEmpty { "分享人：${articleData.shareUser}" },
                                        color = if (articleData.author.isEmpty()) Color.Red.copy(
                                            alpha = 0.5f
                                        ) else primaryLight,
                                        fontSize = 12.sp,
                                    )
                                    Text(
                                        text = articleData.niceDate,
                                        color = tertiary,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }
                                Image(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(
                                        tertiary
                                    )
                                )
                            }
                        }
                    }
                }

            }

            AnimatedVisibility(
                visible = showScrollToTop,
//                enter = fadeIn(animationSpec = tween(durationMillis = 100)),
//                exit = fadeOut(animationSpec = tween(durationMillis = 100)),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
//                    .padding(bottom = 20.dp, end = 10.dp)
//                    .clip(CircleShape)
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            if (listState.firstVisibleItemIndex > 10) {
                                listState.scrollToItem(10)
                            }
                            listState.animateScrollToItem(0)
                        }
                    }, containerColor = mainColor, modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 20.dp, end = 10.dp, start = 10.dp)
//                        .clip(CircleShape)
                ) {
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = primaryLight,
                        modifier = Modifier
                            .size(30.dp)
                            .graphicsLayer {
                                rotationZ = -90f
                            }
                    )
                }
//                Button(
//                    onClick = {
//                        coroutineScope.launch {
//                            if (listState.firstVisibleItemIndex > 10) {
//                                listState.scrollToItem(10)
//                            }
//                            listState.animateScrollToItem(0)
//                        }
//                    },
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape),
//                    colors = ButtonDefaults.buttonColors(mainColor),
//                    shape = CircleShape,
//                    contentPadding = PaddingValues(0.dp)
//
////                    elevation = ButtonDefaults.buttonElevation(
////                        defaultElevation = 10.dp,
////                        pressedElevation = 15.dp,
////                        disabledElevation = 0.dp,
////                        hoveredElevation = 15.dp,
////                        focusedElevation = 10.dp
////                    )
//                ) {
//                    Icon(
//                        Icons.Filled.ArrowForward,
//                        contentDescription = null,
//                        tint = primaryLight,
//                        modifier = Modifier
//                            .size(30.dp)
//                            .graphicsLayer {
//                                rotationZ = -90f
//                            }
//                    )
//                }
            }


//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(bottom = 30.dp, end = 10.dp)
//                    .size(50.dp)
////                .clip(CircleShape)
//                    .background(color = mainColor)
//                    .clickable {
//                        coroutineScope.launch {
//                            if (articleList!!.datas.size > 20) {
//                                listState.scrollToItem(10)
//                                listState.animateScrollToItem(0)
//                            }
//                        }
//                    }, contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    imageVector = Icons.Filled.ArrowForward,
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(primaryLight),
//                    modifier = Modifier
//                        .size(30.dp)
//                        .graphicsLayer {
//                            rotationZ = -90f
//                        }
//                )
//            }
        }

    }

}

@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = alpha),
                        Color.Transparent,
                    )
                )
            )
    )
}

/**
 *layoutInfo.totalItemsCount - 1是最后一个，可以多保留几个比如到倒数第2个的时候就去加载，保证更顺畅
 */
fun LazyListState.isScrolledToEnd(lastReachCount: Int = 1) =
    if (layoutInfo.totalItemsCount < 10 || lastReachCount >= layoutInfo.totalItemsCount) false else
        layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - lastReachCount