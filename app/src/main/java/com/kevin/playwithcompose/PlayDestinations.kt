package com.kevin.playwithcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

interface PlayDestinations {
    val icon: ImageVector
    val route: String
}

object Home : PlayDestinations {
    override val icon: ImageVector
        get() = Icons.Filled.Home
    override val route: String
        get() = "Home"

}

object Project : PlayDestinations {
    override val icon: ImageVector
        get() = Icons.Filled.LocationOn
    override val route: String
        get() = "Project"

}

object Menu : PlayDestinations {
    override val icon: ImageVector
        get() = Icons.Filled.Menu
    override val route: String
        get() = "Menu"

}

object Me : PlayDestinations {
    override val icon: ImageVector
        get() = Icons.Filled.Person
    override val route: String
        get() = "Me"

}
val tabScreens = listOf(Home,Project,Menu,Me)

object Route{
    const val USB_CHECK = "usb_check"
    const val WEB = "web_page"
    const val OPEN_APP = "open_app_page"
    const val SETTINGS = "setting_page"
    const val APP_WIDGET = "app_widget_page"
}