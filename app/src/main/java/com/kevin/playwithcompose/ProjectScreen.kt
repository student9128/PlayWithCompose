package com.kevin.playwithcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevin.playwithcompose.ui.theme.backgroundLight
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.onMainColor
import com.kevin.playwithcompose.ui.theme.primaryLight

@Composable
fun ProjectScreen() {
    val top = WindowInsets.statusBars.getTop(LocalDensity.current)
    val density = LocalDensity.current.density
    Column(modifier = Modifier.padding(top=0.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height((top/density).dp).background(Color.Transparent))
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Text(text="ProjectScreen", color = onMainColor)
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
            Image(
                painter = painterResource(id = R.drawable.ic_avatar),
                contentDescription = "me",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
        }
    }
}
@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mainColor)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val searchText = remember {
            mutableStateOf("")
        }
        var showClearButton by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundLight)
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = onMainColor.copy(alpha = 0.5f)
            )

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
//                        .height(48.dp)
//                        .clip(RoundedCornerShape(16.dp))
                    .padding(start = 10.dp, end = 0.dp),
                value = searchText.value,
                onValueChange = { newText -> searchText.value = newText },
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxSize()
//                            .background(color = Color.Green.copy(alpha = 0.4f))
                    ) {
                        innerTextField()
                    }
                }
            )
        }

    }
}