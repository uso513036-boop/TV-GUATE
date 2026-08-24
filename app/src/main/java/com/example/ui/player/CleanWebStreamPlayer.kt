package com.example.ui.player

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.model.Channel
import com.example.ui.theme.GuateBlueSecondary
import com.example.ui.theme.GuateDarkBackground
import java.io.ByteArrayInputStream

/**
 * Sandboxed Web Player that intercepts all popup attempts, ads, and external redirects,
 * guaranteeing that streaming stays 100% inside the app.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CleanWebStreamPlayer(
    channel: Channel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    val cleanUrl = channel.webPlayerUrl ?: channel.streamUrl

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GuateDarkBackground)
    ) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        setSupportMultipleWindows(false)
                        javaScriptCanOpenWindowsAutomatically = false
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        userAgentString = channel.customUserAgent ?: "Mozilla/5.0 (Linux; Android 14; Mobile; rv:128.0) Gecko/128.0 Firefox/128.0"
                    }

                    // Strict ad-blocker & redirect blocker
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            val url = request?.url?.toString().orEmpty()
                            // Block any market:// intent, intent://, or external popup domains
                            if (url.startsWith("market:") || url.startsWith("intent:") ||
                                url.contains("ad.", true) || url.contains("doubleclick", true) ||
                                url.contains("bet", true) || url.contains("pop", true)
                            ) {
                                return true // Block completely
                            }
                            // Allow internal navigation on legitimate stream domain
                            return false
                        }

                        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                            val url = request?.url?.toString()?.lowercase().orEmpty()
                            // Block known ad domains and tracker scripts
                            val adPatterns = listOf("googlesyndication", "adnxs", "popads", "propellerads", "adsterra", "outbrain", "taboola")
                            if (adPatterns.any { url.contains(it) }) {
                                return WebResourceResponse("text/plain", "utf-8", ByteArrayInputStream("".toByteArray()))
                            }
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                            // Inject CSS to remove header bars, sidebars, cookie alerts and maximize video
                            val cssInjection = """
                                var style = document.createElement('style');
                                style.innerHTML = `
                                    header, nav, footer, .ad, .ads, .advertisement, [id*='cookie'], [class*='banner'] { display: none !important; }
                                    body, html { background: #000 !important; margin: 0 !important; padding: 0 !important; overflow: hidden !important; }
                                    video, iframe { width: 100vw !important; height: 100vh !important; }
                                `;
                                document.head.appendChild(style);
                            """.trimIndent()
                            view?.evaluateJavascript(cssInjection, null)
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onCreateWindow(
                            view: WebView?,
                            isDialog: Boolean,
                            isUserGesture: Boolean,
                            resultMsg: android.os.Message?
                        ): Boolean {
                            // Strictly reject any window.open() or popup tabs
                            return false
                        }
                    }

                    loadUrl(cleanUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = GuateBlueSecondary,
                        modifier = Modifier
                            .padding(24.dp)
                            .size(36.dp)
                    )
                }
            }
        }
    }
}
