package com.kevin.playwithcompose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import kotlin.math.ceil

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun OpenAppPage(navHostController: NavHostController) {
    val context = LocalContext.current
    var dataList by remember { mutableStateOf<ShortcutData?>(null) }
    val n = 3.0f
    val space = 10f
    val contentWidth = (LocalConfiguration.current.screenWidthDp - 32 - (n - 1) * space) / n
    LaunchedEffect(key1 = null) {
        dataList = readJsonFromAssets(context, "shortcut_data.json")

    }
    Scaffold(topBar = {
        AppBar(
            title = "快捷打开第三方应用",
            onBackClick = { navHostController.navigateUp() })
    }) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            dataList?.let {
                it.forEach { section ->
                    stickyHeader {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Color.White)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = section.title,
//                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item(contentType = { section.list }) {
                        Column {
                            val list = section.list
                            FlowRow(
                                Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                                    .wrapContentHeight()
                            ) {
                                list.forEachIndexed { index, shortcutItemData ->
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(Dp(16f)),
                                        elevation = CardDefaults.cardElevation(1.dp),
                                        modifier = Modifier
                                            .padding(
                                                start = if (index % n.toInt() != 0) Dp(space) else 0.dp,
                                                top = 16.dp,
                                                bottom = if(index==list.size-1) 16.dp else 0.dp
                                            )
                                            .width(Dp(contentWidth))
                                            .aspectRatio(1.0f)
                                            .clip(RoundedCornerShape(Dp(16f)))//解决水波纹效果为矩形的问题
                                            .clickable {
                                                val intent = Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(shortcutItemData.url)
                                                )
                                                val packageManager = context.packageManager
                                                val activities =
                                                    packageManager.queryIntentActivities(intent, 0)
                                                if (activities.isNotEmpty()) {
                                                    // 如果支付宝已安装，则通过 Intent 打开
                                                    context.startActivity(intent)
                                                } else {
                                                    // 如果未安装支付宝，则提示用户
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "未安装${section.title}",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            }) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = shortcutItemData.title)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }

    }
}

fun readJsonFromAssets(context: Context, fileName: String): ShortcutData? {
    return try {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val listType = object : TypeToken<ShortcutData>() {}.type
        gson.fromJson<ShortcutData>(reader, listType)
    } catch (e: Exception) {
        Log.e("Assets", "Error reading JSON from assets", e)
        null
    }
}

class ShortcutData : ArrayList<ShortcutItem>()

//data class ShortcutItem(
//    val list: ShortcutItemList,
//    val key: String,
//    val title: String
//)

data class ShortcutItemData(
    val key: String,
    val title: String,
    val url: String
)


data class ShortcutItem(
    val key: String,
    val list: List<ShortcutItemData>,
    val title: String
)
