package com.kevin.playwithcompose

import android.content.Context
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevin.playwithcompose.bean.NavListData
import com.kevin.playwithcompose.ui.theme.backgroundLight
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.onMainColor
import com.kevin.playwithcompose.ui.theme.primaryLight
import com.kevin.playwithcompose.util.LogUtils.printD
import com.kevin.playwithcompose.viewmodel.MenuViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun MenuScreen(context: Context) {
    val top = WindowInsets.statusBars.getTop(LocalDensity.current)
    val density = LocalDensity.current.density
    val viewModel = MenuViewModel()
    val navListData: List<NavListData> by viewModel.navTitleListData

Scaffold {innerPadding->
    Column(modifier = Modifier.padding(innerPadding)) {
        SearchBar()
        Row {
            var selectedIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            val titleListState = rememberLazyListState()
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = listState, key2 = selectedIndex) {
                listState.scrollToItem(selectedIndex)
                snapshotFlow { listState.firstVisibleItemIndex }
                    .distinctUntilChanged()
                    .collect { index ->
                        selectedIndex = index
                        val layoutInfo = titleListState.layoutInfo
                        val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
                        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        if (index !in firstVisibleItem..lastVisibleItem) {
                            titleListState.scrollToItem(index)
                        }
                    }
            }
            LazyColumn(
                state = titleListState,
                modifier = Modifier
                    .weight(1f)
                    .background(backgroundLight)
            ) {
                items(count = navListData.size) { index ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topEnd = if (index == selectedIndex + 1) 10.dp else 0.dp,
                                bottomEnd = if (index == selectedIndex - 1) 10.dp else 0.dp
                            )
                        )
                        .background(
                            color = if (selectedIndex == index) backgroundLight else Color(
                                0xFFFCBAAD
                            )
                        )
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                            selectedIndex = index
                            coroutineScope.launch {
                                listState.scrollToItem(index)
                            }

                        }
                        .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = navListData[index].name,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        )
                    }
                }
            }
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(3f),
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                items(count = navListData.size) { index ->
                    val item = navListData[index]
                    Column(modifier = Modifier.padding(vertical = 10.dp)) {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        FlowRow {
                            item.articles.forEach { item ->
                                Text(
                                    text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                                        .toString(),
                                    modifier = Modifier
                                        .padding(start = 10.dp, top = 10.dp)
                                        .clip(
                                            RoundedCornerShape(30.dp)
                                        )
                                        .background(generateColor())
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }


        }
    }

}


}

fun generateColor(): Color {
    val random = Random(Random.nextLong())
    val color = listOf(
        Color(0xFFEF9595),
        Color(0xFF75C2F6),
        Color(0xFFEFD595),
        Color(0xFFFF9B50),
        Color(0xFFA2C579),
        Color(0xFF64CCC5),
        Color(0xFFD7E5CA),
        Color(0xFF3AA6B9),
        Color(0xFFEFB495),
        Color(0xFFACBCFF),
        Color(0xFF994D1C),
    )
    return color[random.nextInt(color.size)]
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
                .clip(RoundedCornerShape(24.dp))
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