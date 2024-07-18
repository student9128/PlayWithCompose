package com.kevin.playwithcompose

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.onMainColor
import com.kevin.playwithcompose.ui.theme.primaryLight
import com.kevin.playwithcompose.util.LogUtils.printI
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    context: Context = LocalContext.current.applicationContext,
    navHostController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val toolbarHeight = 200.dp
    val toolbarOffsetHeightPx = rememberSaveable { mutableStateOf(0f) }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                printI("y==========${available.y},${newOffset},${toolbarOffsetHeightPx.value}")
                return Offset.Zero
            }
        }
    }
//    Column {
    val brush = Brush.horizontalGradient(
        listOf(Color(0xFFFDE5EC), Color(0xFFFCBAAD), mainColor),
//            start = Offset(0f, 0f),
//            end = Offset.Infinite,
        tileMode = TileMode.Clamp
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(nestedScrollConnection)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
//                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(brush)
        )
        LazyColumn(
//            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
//                    modifier = Modifier.padding(top = 16.dp)
        ) {
            item {
                HeaderView()

            }
            items(count = 100) { index ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable {
                            coroutineScope.launch {
                                //welcomeActivity的launchMode设置为singleStance点击更换图标不会退出app
                                val packageManager = context.packageManager
                                if (index == 0) {
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomePinkActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivityAlias"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )

                                } else if (index == 1) {
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomePinkActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivityAlias"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                } else if (index == 2) {
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomePinkActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivity"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                    packageManager.setComponentEnabledSetting(
                                        ComponentName(
                                            "com.kevin.playwithcompose",
                                            "com.kevin.playwithcompose.WelcomeActivityAlias"
                                        ),
                                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP
                                    )
                                }
                            }
                            Toast
                                .makeText(context, "点击了$index", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Image(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = if (index % 2 == 0) Color.Red.copy(
                                alpha = 0.5f
                            ) else primaryLight
                        )
                    )
                    Text(text = "index = $index")
                }
            }
        }

//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopCenter),
//                horizontalAlignment = Alignment.CenterHorizontally
//
//            ) {
//
////                Button(onClick = { navHostController.navigate(Route.USB_CHECK) }) {
////                    Text(text = "检测手机是否连接usb")
////                }
////                Button(onClick = {showBottomSheet=true }) {
////                    Text(text = "显示BottomSheet")
////                }
////                if(showBottomSheet){
////                    ModalBottomSheet(
////                        onDismissRequest = {
////                            showBottomSheet = false
////                        },
////                        sheetState = sheetState,
//////                        scrimColor = Color.Transparent,
////                        windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)//modal background color overlay status bar
////                    ) {
////                        // Sheet content
////                        Button(onClick = {
////                            scope.launch { sheetState.hide() }.invokeOnCompletion {
////                                if (!sheetState.isVisible) {
////                                    showBottomSheet = false
////                                }
////                            }
////                        }) {
////                            Text("Hide bottom sheet")
////                        }
////                    }
////                }
//
//            }
        AppBar(
            title = "一起学习Compose",
            showBackIcon = false,
            modifier = Modifier.alpha(toolbarOffsetHeightPx.value.absoluteValue / toolbarHeightPx)
        )
    }

}

@Composable
fun HeaderView() {
    val imageBorder = Brush.verticalGradient(
//            listOf(Color(0xFF6F1E9), Color(0xFFFFD93D), Color(0xFFFF8400), Color(0xFF4F200D)),
        listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta),
        tileMode = TileMode.Clamp
    )
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(color = Color.White)
        )
        Column(
            modifier = Modifier.padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, brush = imageBorder, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatar),
                    contentDescription = "me",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Text(
                text = "一起学习Compose",
                color = onMainColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
//                        .background(Color.Green.copy(alpha = 0.5f))

            )
        }
    }
}