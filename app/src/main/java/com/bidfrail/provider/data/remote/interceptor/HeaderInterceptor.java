package com.bidfrail.provider.data.remote.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    public static final String HEADER_KEY_ACCEPT = "Accept";
    public static final String HEADER_KEY_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_KEY_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEAD_VALUE_ACCEPT_ENCODING = "gzip, deflate";
    public static final String HEADER_KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_KEY_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_KEY_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_KEY_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_KEY_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_KEY_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_KEY_USER_AGENT = "User-Agent";
    public static final String HEADER_KEY_AUTHORIZATION = "Authorization";
    public static final String HEAD_KEY_CONNECTION = "Connection";
    public static final String HEAD_VALUE_CONNECTION_KEEP_ALIVE = "keep-alive";
    public static final String HEAD_VALUE_CONNECTION_CLOSE = "close";
    public static final String HEAD_KEY_DATE = "Date";
    public static final String HEAD_KEY_EXPIRES = "Expires";
    public static final String HEAD_KEY_E_TAG = "ETag";
    public static final String HEAD_KEY_PRAGMA = "Pragma";
    public static final String HEAD_KEY_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String HEAD_KEY_IF_NONE_MATCH = "If-None-Match";
    public static final String HEAD_KEY_LAST_MODIFIED = "Last-Modified";
    public static final String HEAD_KEY_LOCATION = "Location";
    public static final String HEAD_KEY_USER_AGENT = "User-Agent";
    public static final String HEAD_KEY_COOKIE = "Cookie";
    public static final String HEAD_KEY_COOKIE2 = "Cookie2";
    public static final String HEAD_KEY_SET_COOKIE = "Set-Cookie";
    public static final String HEAD_KEY_SET_COOKIE2 = "Set-Cookie2";

    private HashMap<String, String> headers;

    public HeaderInterceptor(HashMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        builder.addHeader("req_time", timestamp);

        return chain.proceed(builder.build());
    }
}