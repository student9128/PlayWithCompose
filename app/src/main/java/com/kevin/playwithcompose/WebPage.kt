package com.kevin.playwithcompose

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.kevin.playwithcompose.util.LogUtils.printD

@Composable
fun WebPage(navHostController: NavHostController, url: String) {
    LaunchedEffect(key1 = "") {
        printD("first===$url")
    }
    Scaffold(topBar = {
        AppBar(
            title = "Web",
            onBackClick = { navHostController.navigateUp() })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return false
                        }
                    }
                }
            },
//                modifier = Modifier.fillMaxSize().background(color = Color.Blue),
                update = {
                    it.loadUrl(url)
//                    it.loadUrl("https://www.baidu.com/")
                })
        }

    }
}