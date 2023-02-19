package com.library.webview;

import android.graphics.Bitmap;
import android.webkit.WebView;

public interface WebViewClientCallback {
    void onPageStarted(WebView webView, String url, Bitmap favicon);
    boolean shouldOverrideUrlLoading(WebView webView, String url);
    void onPageFinished(WebView webView, String url);
    void onReceivedError(WebView webView, int errorCode, String description, String failingUrl);
}