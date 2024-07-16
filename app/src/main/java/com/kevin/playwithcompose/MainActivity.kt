package com.kevin.playwithcompose

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kevin.playwithcompose.base.BaseActivity
import com.kevin.playwithcompose.navigation.PlayNavHost
import com.kevin.playwithcompose.ui.theme.PlayWithComposeTheme
import com.kevin.playwithcompose.ui.theme.backgroundLight
import com.kevin.playwithcompose.ui.theme.mainColor

class MainActivity : BaseActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
        setContent {
            val bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current)
            val top = WindowInsets.statusBars.getTop(Density(this))
            val density = LocalDensity.current.density
            Log.d("MainActivity", "top======${top / density},bottom=${bottom / density}")
            PlayWithComposeTheme {
                val bottomBarState = rememberSaveable { mutableStateOf(true) }
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val destination = currentBackStack?.destination
                val currentScreen = tabScreens.find { it.route == destination?.route } ?: Home
                bottomBarState.value =
                    destination?.route == Home.route || destination?.route == Project.route || destination?.route == Menu.route || destination?.route == Me.route || destination?.route == null
                Scaffold(
                    bottomBar = {
                        PlayTabs(
                            allScreens = tabScreens,
                            currentScreen = currentScreen,
                            onTabSelected = { newScreen ->
                                navController.navigate(route = newScreen.route) {
                                    print("zou le ma")
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                if (newScreen.route == Project.route) {
                                    window.statusBarColor = backgroundLight.toArgb()
                                } else if (newScreen.route == Me.route) {
                                    window.statusBarColor =
                                        Color.Transparent.toArgb() // 设置StatusBarColor为透明
                                } else {
                                    window.statusBarColor = mainColor.toArgb()
                                }
                            }, bottomBarVisible = bottomBarState.value
                        )
                    }) { innerPadding ->
                    PlayNavHost(
                        context = this@MainActivity,
                        navController = navController,
                        modifier = Modifier.padding(
                            start = innerPadding.calculateLeftPadding(LayoutDirection.Ltr),
                            top = 0.dp,
                            end = innerPadding.calculateLeftPadding(LayoutDirection.Ltr),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PlayTabs(
    allScreens: List<PlayDestinations>,
    onTabSelected: (PlayDestinations) -> Unit,
    currentScreen: PlayDestinations,
    bottomBarVisible: Boolean
) {
    AnimatedVisibility(
        visible = bottomBarVisible,
        enter = slideInVertically(initialOffsetY = {it}),
        exit = slideOutVertically(targetOffsetY = {it})
    ) {
        val bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current)
        val density = LocalDensity.current.density
        Log.d("MainActivity", "top======,bottom=${bottom / density}")
        Surface(
            color = Color(0xFFF1EFEF),
            modifier = Modifier
                .height((56 + bottom / density).dp)
                .fillMaxWidth()
                .navigationBarsPadding()
//            .blur(radiusX = 1.dp, radiusY = 1.dp)
        ) {
            Row(
                Modifier.selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                allScreens.forEach { screen ->
                    PlayTab(
                        screen = screen,
                        onTabSelected = { onTabSelected(screen) },
                        selected = currentScreen == screen
                    )
                }
            }
        }
    }
}

@Composable
fun PlayTab(screen: PlayDestinations, onTabSelected: () -> Unit, selected: Boolean) {
    val color = Color(0xFF5B9A8B)
    val duration = if (selected) 100 else 150
    val animSpec =
        remember {
            tween<Color>(
                durationMillis = duration,
                easing = LinearEasing,
                delayMillis = 10
            )
        }
    val tabColor by animateColorAsState(
        targetValue = if (selected) color else Color(0xCC61677A),
        animationSpec = animSpec,
        label = screen.route
    )
    val tabWidth = Dp(LocalConfiguration.current.screenWidthDp / 4 * 1f)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .width(
                tabWidth
            )
            .fillMaxHeight()
            .animateContentSize()
            .selectable(
                selected = selected,
                onClick = onTabSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = tabWidth / 2)
            )
    ) {
        Icon(imageVector = screen.icon, contentDescription = screen.route, tint = tabColor)
        Text(text = screen.route, color = tabColor, fontSize = 14.sp)

    }
}


@Composable
fun MeScreenX() {
    Column {
        Text(text = "MeScreen")
    }
}
