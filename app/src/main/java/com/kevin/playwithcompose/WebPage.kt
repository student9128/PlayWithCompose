package com.kevin.playwithcompose

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.kevin.playwithcompose.util.LogUtils.printD

@Composable
fun WebPage(navHostController: NavHostController, url: String, title: String = "") {
    var xTitle by remember {
        mutableStateOf("")
    }
    var progressBarVisible by remember {
        mutableStateOf(false)
    }
    var progressBarProgress by remember {
        mutableStateOf(0.0f)
    }
    LaunchedEffect(key1 = "") {
        printD("first====title==$url,title=$title")
        xTitle = title
    }
    Scaffold(topBar = {
        AppBar(
            title = xTitle,
            onBackClick = { navHostController.navigateUp() })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box {
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

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                progressBarVisible = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                progressBarVisible = false
                                val webTitle = view?.title
                                if (xTitle.isEmpty()) {
                                    xTitle = webTitle ?: ""
                                }
                            }
                        }
                        settings.javaScriptEnabled = true
                        settings.supportZoom()
                        settings.useWideViewPort = true
                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                super.onProgressChanged(view, newProgress)
                                progressBarProgress = newProgress.toFloat()
                            }
                        }

                    }
                },
                    update = {
                        it.loadUrl(url)
                    })
                if (progressBarVisible) {
                    LinearProgressIndicator(
                        progress = { progressBarProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                    )
                }
            }
        }

    }
}