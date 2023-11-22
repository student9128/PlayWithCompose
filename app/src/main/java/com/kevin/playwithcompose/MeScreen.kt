package com.kevin.playwithcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.onMainColor
import com.kevin.playwithcompose.ui.theme.primaryLight

@Composable
fun MeScreen() {
    Column {
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .background(Color.Red))
//        val brush = Brush.linearGradient(
//            listOf(Color(0xFFFDE5EC), Color(0xFFFCBAAD), mainColor),
//            start = Offset(0f, 0f),
//            end = Offset.Infinite,
//            tileMode = TileMode.Clamp
//        )
        val brush = Brush.horizontalGradient(
            listOf(Color(0xFFFDE5EC), Color(0xFFFCBAAD), mainColor),
//            start = Offset(0f, 0f),
//            end = Offset.Infinite,
            tileMode = TileMode.Clamp
        )
        val imageBorder = Brush.verticalGradient(
//            listOf(Color(0xFF6F1E9), Color(0xFFFFD93D), Color(0xFFFF8400), Color(0xFF4F200D)),
            listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta),
            tileMode = TileMode.Clamp
        )
        Box {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(brush)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = Color.White)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 150.dp),
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
                LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.padding(top = 16.dp)) {
                    items(count = 10) { index ->
                        Row(Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                            Image(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color = if(index%2==0)Color.Red.copy(alpha = 0.5f) else primaryLight)
                            )
                            Text(text = "index = $index")
                        }
                    }
                }
            }
        }
        Text("MeScreen")
    }
}