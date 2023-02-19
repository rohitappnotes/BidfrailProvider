package com.library.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = MyWebViewClient.class.getSimpleName();

    private final WebViewClientCallback webViewClientCallback;

    public MyWebViewClient(WebViewClientCallback webViewClientCallback) {
        super();
        this.webViewClientCallback = webViewClientCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return webViewClientCallback.shouldOverrideUrlLoading(webView, url);
    }

    /**
     * Since API level 24, public boolean shouldOverrideUrlLoading(WebView view, String url) replaced to
     * public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request).
     *
     * However, public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request), added
     * in API level 24.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String url = webResourceRequest.getUrl().toString();
        return webViewClientCallback.shouldOverrideUrlLoading(webView, url);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        webViewClientCallback.onPageStarted(webView, url, favicon);
        super.onPageStarted(webView, url, favicon);
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        webViewClientCallback.onPageFinished(webView, url);
        super.onPageFinished(webView, url);
    }

    @Override
    public void onLoadResource(WebView webView, String url) {
        super.onLoadResource(webView, url);
    }

    @Override
    public void onPageCommitVisible(WebView webView, String url) {
        super.onPageCommitVisible(webView, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        return super.shouldInterceptRequest(webView, url);
    }

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override
    public void onTooManyRedirects(WebView webView, Message cancelMessage, Message continueMessage) {
        super.onTooManyRedirects(webView, cancelMessage, continueMessage);
    }

    /*
     * api < 23
     */
    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        Log.i(TAG, "Error Code api < 23 : " + errorCode);
        webViewClientCallback.onReceivedError(webView, errorCode, description, failingUrl);
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }

    /*
     * api > 23
     */
    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView webView, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(webView, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(webView, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView webView, Message dontResend, Message resend) {
        super.onFormResubmission(webView, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView webView, String url, boolean isReload) {
        super.doUpdateVisitedHistory(webView, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(webView, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest request) {
        super.onReceivedClientCertRequest(webView, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(webView, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent event) {
        return super.shouldOverrideKeyEvent(webView, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView webView, KeyEvent event) {
        super.onUnhandledKeyEvent(webView, event);
    }

    @Override
    public void onScaleChanged(WebView webView, float oldScale, float newScale) {
        super.onScaleChanged(webView, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView webView, String realm, @Nullable String account, String args) {
        super.onReceivedLoginRequest(webView, realm, account, args);
    }

    @Override
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail detail) {
        return super.onRenderProcessGone(webView, detail);
    }

    @Override
    public void onSafeBrowsingHit(WebView webView, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        super.onSafeBrowsingHit(webView, request, threatType, callback);
    }
}
