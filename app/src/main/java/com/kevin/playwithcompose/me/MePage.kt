package com.kevin.playwithcompose.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kevin.playwithcompose.R
import com.kevin.playwithcompose.ui.widget.PlayAppBar

@Composable
fun SettingPage(navHostController: NavHostController) {
    val iconList =
        listOf(
            mapOf("icon" to R.drawable.ic_launcher, "title" to "默认", "checked" to true),
            mapOf("icon" to R.drawable.ic_launcher_pink, "title" to "图标1", "checked" to false),
            mapOf("icon" to R.drawable.ic_launcher_orange, "title" to "图标2", "checked" to false),
        )
    PlayAppBar(title = "设置") { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Text("修改图标")
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Fixed(4)
            ) {
                items(iconList.size) { index ->
                    val map = iconList[index]
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.border(
                                width = 2.dp,
                                color = if (map["checked"] as Boolean) Color.Green else Color.Transparent,
                                shape = RoundedCornerShape(5.dp)
                            )
                        ) {
                            Image(
                                painter = painterResource(map["icon"] as Int),
                                contentDescription = "${map["title"]}"
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                        Text("${map["title"]}")
                    }
                }
            }
        }
    }

}

@Composable
fun AppWidgetPage(navHostController: NavHostController) {
    PlayAppBar(title = "小组件") { }

}