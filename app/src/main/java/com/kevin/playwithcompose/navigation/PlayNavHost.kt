package com.kevin.playwithcompose.navigation

import android.content.Context
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
    NavHost(navController = navController, modifier = modifier, startDestination = Home.route) {
        composable(route = Home.route) {
            HomeScreen()
        }
        composable(route = Project.route) {
            ProjectScreen()
        }
        composable(route = Menu.route) {
            MenuScreen(context)
        }
        composable(route = Me.route) {
            MeScreen(navHostController = navController)
        }
        composable(route=Route.USB_CHECK){
            UsbCheckPage(navHostController = navController)
        }
    }
}