package com.kevin.playwithcompose.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kevin.playwithcompose.Home
import com.kevin.playwithcompose.HomeScreen
import com.kevin.playwithcompose.Me
import com.kevin.playwithcompose.MeScreen
import com.kevin.playwithcompose.Menu
import com.kevin.playwithcompose.MenuScreen
import com.kevin.playwithcompose.Project
import com.kevin.playwithcompose.ProjectScreen
import com.kevin.playwithcompose.Route
import com.kevin.playwithcompose.UsbCheckPage

@Composable
fun PlayNavHost(navController: NavHostController, modifier: Modifier = Modifier, context: Context) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        }) {
        composable(
            route = Home.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }) {
            HomeScreen()
        }
        composable(route = Project.route, enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }) {
            ProjectScreen()
        }
        composable(route = Menu.route, enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) },
            popEnterTransition = { fadeIn(animationSpec = tween(700)) },
            popExitTransition = { fadeOut(animationSpec = tween(700)) }) {
            MenuScreen(context)
        }
        composable(route = Me.route, enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }) {
            MeScreen(navHostController = navController)
        }
        composable(route = Route.USB_CHECK) {
            UsbCheckPage(navHostController = navController)
        }
    }
}