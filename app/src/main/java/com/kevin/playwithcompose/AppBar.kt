package com.kevin.playwithcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kevin.playwithcompose.ui.theme.mainColor
import com.kevin.playwithcompose.ui.theme.onMainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, showBackIcon: Boolean = true, onBackClick: (() -> Unit)? = null) {
    val navController: NavHostController = rememberNavController()
    CenterAlignedTopAppBar(
        title = { Text(text = title, maxLines=1, overflow = TextOverflow.Ellipsis) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = mainColor,
            titleContentColor = onMainColor
        ),
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}