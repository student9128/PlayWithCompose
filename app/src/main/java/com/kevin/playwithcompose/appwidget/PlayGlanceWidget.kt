package com.kevin.playwithcompose.appwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class PlayGlanceWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                GlanceContent(context)
            }

        }
    }

    @Composable
    fun GlanceContent(context: Context) {
        val size = LocalSize.current
        val text = remember { mutableStateOf("Hello Glance") }
        Column(modifier = GlanceModifier.fillMaxSize().background(Color.White.copy(alpha = 0.5f))) {
            if (size.height >= BIG_SQUARE.height) {
                Text(text = "Where to?", modifier = GlanceModifier.padding(12.dp))
            }
            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(text = "Hello", onClick = {})
                if (size.width >= HORIZONTAL_RECTANGLE.width) {
                    Text("School")
                }
            }
            if (size.height >= BIG_SQUARE.height) {
                Text(text = "provided by X")
            }
        }
    }
}