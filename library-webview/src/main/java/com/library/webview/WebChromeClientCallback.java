package com.library.webview;

import android.webkit.WebView;

public interface WebChromeClientCallback {
    void onProgress(WebView webView, int newProgress);
    void onPageTitle(String pageTitle);
}