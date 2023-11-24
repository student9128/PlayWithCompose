package com.kevin.playwithcompose

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class WelcomeActivity : ComponentActivity() {
    private val viewModel: WelcomeVideModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
//                viewModel.showSplash.value
                false
            }
        }
        super.onCreate(savedInstanceState)
//        setContent {
//            WelcomeContent()
//        }

        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WelcomeContent() {
        Image(
            painter = painterResource(R.drawable.ic_splash),
            contentDescription = "splash",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
//        Scaffold {
//                innerPadding->
//            Column(modifier = Modifier.padding(innerPadding)) {
//                Text("Hello")
//            }
//        }
    }
}

class WelcomeVideModel : ViewModel() {
    private val _showSplash = MutableStateFlow(true)
    val showSplash = _showSplash.asStateFlow()

    init {
        viewModelScope.launch {
            delay(10)
            _showSplash.value = false
        }
    }
}
