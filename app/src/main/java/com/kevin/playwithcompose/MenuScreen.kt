package com.kevin.playwithcompose

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(context: Context) {
    val top = WindowInsets.statusBars.getTop(LocalDensity.current)
    val density = LocalDensity.current.density
    Column(modifier = Modifier.padding((top/density).dp)) {
        Text("MenuScreen")
    }
}