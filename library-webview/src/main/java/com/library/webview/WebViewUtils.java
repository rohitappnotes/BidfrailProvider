package com.library.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtils {

    public static final String TAG = WebViewUtils.class.getSimpleName();

    private WebViewUtils() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    public static void setDesktopMode(WebView webView, boolean enabled) {
        WebSettings webSettings = webView.getSettings();
        String newUserAgent = webSettings.getUserAgentString();
        Log.e(TAG, "setDesktopMode: current mode:" + newUserAgent);
        if (enabled) {
            newUserAgent = WebViewConstants.USER_AGENT_ONE;
        } else {
            newUserAgent = null;
        }
        webSettings.setUserAgentString(newUserAgent);
        webSettings.setUseWideViewPort(enabled); // makes the WebView have a normal viewport (such as a normal desktop browser), while when false the webview will have a viewport constrained to its own dimensions (so if the WebView is 50px*50px the viewport will be the same size)
        webSettings.setLoadWithOverviewMode(enabled); // loads the WebView completely zoomed out
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebViewSettings(Context context, WebView webView) {

        WebSettings webSettings = webView.getSettings();

        webSettings.setUserAgentString(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(true);
        }

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBlockNetworkImage(false);

        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(context.getCacheDir().getAbsolutePath());

        /*
         * LOAD_CACHE_ONLY:
         *
         * Do not use the network, only read the local cache data
         *
         * LOAD_DEFAULT:
         *
         * (default) The default loading method. Using this method, fast forward and backward will
         * be realized. After opening several web pages in the same tab, when the network is closed,
         * you can switch the accessed data by forward and backward. At the same time, new web pages
         * require network
         *
         * LOAD_NO_CACHE: Do not use cache, only get data from the network. No cache is used. If there
         * is no network, the previous webpage will not be used even if the webpage has been opened
         * before.
         *
         * LOAD_CACHE_ELSE_NETWORK, as long as it is locally available, regardless of whether it is
         * expired or no-cache, the data in the cache is used.
         */
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    public static void setWebView(Context context, WebView webView, MyWebViewClient myWebViewClient, MyWebChromeClient myWebChromeClient, DownloadCallback downloadCallback) {

        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(myWebChromeClient);

        setWebViewSettings(context, webView);

        webView.canGoBack();
        webView.canGoForward();
        webView.setLongClickable(false);
        webView.setFocusable(true);
        webView.setDrawingCacheEnabled(true);
        webView.setFocusableInTouchMode(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                downloadCallback.onDownloadStart(url, userAgent, contentDisposition, mimeType, contentLength);
            }
        });
    }
}
