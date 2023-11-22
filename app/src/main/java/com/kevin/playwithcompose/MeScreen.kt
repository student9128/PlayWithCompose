package com.kevin.playwithcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kevin.playwithcompose.ui.theme.mainColor
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
            ) {

            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 150.dp)
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
        }
        Text("MeScreen")
    }
}