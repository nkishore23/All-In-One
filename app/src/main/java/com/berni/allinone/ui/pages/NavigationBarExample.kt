package com.berni.allinone.ui.pages

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun NavigationBarExample() {
    val tabs = listOf("Web URL", "Local URL")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    // Create and remember both WebViews
    val homeWebView by remember {
        mutableStateOf(
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl("https://www.google.com")
            }
        )
    }

    val profileWebView by remember {
        mutableStateOf(
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl("file:///android_asset/index.html")
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, color = Color.White) }
                )
            }
        }

        // Show both but hide unselected one using alpha
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { homeWebView },
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = if (selectedTabIndex == 0) 1f else 0f }
            )

            AndroidView(
                factory = { profileWebView },
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = if (selectedTabIndex == 1) 1f else 0f }
            )
        }
    }
}
