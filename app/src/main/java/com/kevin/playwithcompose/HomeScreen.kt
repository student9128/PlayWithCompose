package com.kevin.playwithcompose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.text.Html
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kevin.composestudy.bean.BannerData
import com.kevin.playwithcompose.bean.ArticleData
import com.kevin.playwithcompose.bean.ArticleListData
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.primaryLight
import com.kevin.playwithcompose.ui.theme.tertiary
import com.kevin.playwithcompose.ui.widget.Swiper
import com.kevin.playwithcompose.util.LogUtils.printD
import com.kevin.playwithcompose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context = LocalContext.current.applicationContext,
    listState: LazyListState
) {
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
//    val listState = rememberLazyListState()
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
    Scaffold(topBar = { AppBar(title = "Home", showBackIcon = false) }) { innerPadding ->
        Box {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            ) {
                item{
                    Box(modifier = Modifier.padding(horizontal = 10.dp)){
                    Swiper(list = pagerList)
                    }
                }

                items(
                    count = articleList?.datas?.size ?: 0,
                    contentType = { index -> articleList?.datas?.get(index) }) { index ->
                    val articleData = articleList!!.datas[index]
                    val decodedText =
                        Html.fromHtml(articleData.title, Html.FROM_HTML_MODE_LEGACY).toString()
                    BuildArticleItem(articleList, index, navController, articleData, decodedText)
                }

            }

            AnimatedVisibility(
                visible = showScrollToTop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
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
@OptIn(ExperimentalLayoutApi::class)
private fun BuildArticleItem(
    articleList: ArticleListData?,
    index: Int,
    navController: NavHostController,
    articleData: ArticleData,
    decodedText: String
) {
    val ctx = LocalContext.current
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerSize(10.dp)))
            .clickable() {
                val data = articleList?.datas?.get(index)
                val url = data?.link
                val title = data?.title
                openTab(ctx, url!!);
//                navController.navigate(
//                    Route.WEB + "/${
//                        URLEncoder.encode(
//                            url,
//                            StandardCharsets.UTF_8.toString()
//                        )
//                    }?${title}"
//                )
            },
        colors = CardDefaults.cardColors(
            containerColor = if (articleData.author.isEmpty()) Color(0xffEBF3E8) else Color(
                0xffF4EEEE
            )
        )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp),
                        colorFilter = ColorFilter.tint(
                            color = if (articleData.author.isEmpty()) Color.Red.copy(
                                alpha = 0.5f
                            ) else primaryLight.copy(alpha = 0.8f)
                        )
                    )
                    Column {
                        Text(
                            text = articleData.author.ifEmpty { "${articleData.shareUser} (分享人)" },
                            color = if (articleData.author.isEmpty()) Color.Red.copy(
                                alpha = 0.5f
                            ) else primaryLight,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = articleData.niceDate,
                            color = tertiary,
                            fontSize = 12.sp,
                        )
                    }
                }
                Image(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        tertiary.copy(alpha = 0.5f)
                    )
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = decodedText,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (articleData.tags.isNotEmpty()) {
                FlowRow(modifier = Modifier.padding(vertical = 10.dp)) {
                    articleData.tags.forEach { item ->
                        Text(
                            text = item.name,
                            color = primaryLight,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = primaryLight,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 0.dp)
                        )
                    }
                }
            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row {
//                    Text(
//                        text = articleData.author.ifEmpty { "分享人：${articleData.shareUser}" },
//                        color = if (articleData.author.isEmpty()) Color.Red.copy(
//                            alpha = 0.5f
//                        ) else primaryLight,
//                        fontSize = 12.sp,
//                    )
//                    Text(
//                        text = articleData.niceDate,
//                        color = tertiary,
//                        fontSize = 12.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                }
//                Image(
//                    imageVector = Icons.Outlined.FavoriteBorder,
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(
//                        tertiary
//                    )
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

fun openTab(context: Context,url:String) {
    val pm = context.packageManager
//    val filter = IntentFilter(Intent.ACTION_VIEW)
//    filter.addCategory(Intent.CATEGORY_BROWSABLE)
//    val activities: List<ResolveInfo> = ArrayList()
//    printD("Hello===pm=${pm}")
//    pm?.let {
//    val resolveInfo:List<ResolveInfo> = pm.getPreferredActivities(filter,activities)
//        printD("Hello===2=${resolveInfo.size}")
//        resolveInfo.let {
//            if(it.isNotEmpty()){
//              val ri =   it[0]
//                printD("Hello===${ri.activityInfo.packageName}")
//            }
//        }
//    }
    val uri = Uri.parse("http://www.example.com") // Replace with any URL
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val packageManager = context.packageManager

    val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    printD("llllll======${resolveInfo?.activityInfo?.packageName}")
    val package_name = "com.android.chrome"

    val activity = (context as? Activity)
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)

    builder.setInstantAppsEnabled(true)

//    builder.setToolbarColor(ContextCompat.getColor(context, R.color.purple_200))
    val colorScheme = CustomTabColorSchemeParams.Builder().setToolbarColor(mainColor.toArgb()).build()
    builder.setDefaultColorSchemeParams(colorScheme)
    builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

    val customBuilder = builder.build()

    if (package_name != null) {
        customBuilder.intent.setPackage(package_name)
        printD("Hello======")

        customBuilder.launchUrl(context, Uri.parse(url))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        activity?.startActivity(i)
    }

}