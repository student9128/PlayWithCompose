package com.kevin.playwithcompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeActivity:ComponentActivity() {
    private val viewModel :WelcomeVideModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
//                viewModel.showSplash.value
                true
            }
        }
        super.onCreate(savedInstanceState)
//        setContent {
//            WelcomeContent()
//        }
        lifecycleScope.launch {
            delay(3000)
            val intent = Intent(this@WelcomeActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WelcomeContent(){
        Scaffold {
                innerPadding->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Hello")
            }
        }
    }
}
class WelcomeVideModel:ViewModel(){
    private val _showSplash = MutableStateFlow(true)
    val showSplash = _showSplash.asStateFlow()
    init {
        viewModelScope.launch {
            delay(10)
            _showSplash.value =false
        }
    }
}
