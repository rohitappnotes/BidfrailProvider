package com.library.webview;

public interface DownloadCallback {
    void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength);
}