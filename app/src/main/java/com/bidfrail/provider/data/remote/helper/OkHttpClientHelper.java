package com.bidfrail.provider.data.remote.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.bidfrail.provider.data.remote.cache.CacheCreator;
import com.bidfrail.provider.data.remote.interceptor.ApiKeyInterceptor;
import com.bidfrail.provider.data.remote.interceptor.BearerAuthenticationInterceptor;
import com.bidfrail.provider.data.remote.interceptor.HeaderInterceptor;
import com.bidfrail.provider.data.remote.interceptor.ProvideCacheInterceptor;
import com.bidfrail.provider.data.remote.interceptor.ProvideOfflineCacheInterceptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import static android.util.Log.VERBOSE;

public class OkHttpClientHelper {

    @SuppressLint("StaticFieldLeak")
    private static OkHttpClientHelper instance;

    public static OkHttpClientHelper getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientHelper.class) {
                if (instance == null) {
                    instance = new OkHttpClientHelper();
                }
            }
        }
        return instance;
    }

    public class Builder {

        private final String TAG = "OkHttpClientHelper";

        /*
         * Note :  We can set timeouts settings on the underlying HTTP client.
         * If we don’t specify a client, Retrofit will create one with default connect and read timeouts.
         * By default, Retrofit uses the following timeouts :
         *                                                      Connection timeout: 10 seconds
         *                                                      Read timeout: 10 seconds
         *                                                      Write timeout: 10 seconds
         */
        private int DEFAULT_HTTP_CONNECT_TIMEOUT_IN_SECONDS             = 60;
        private int DEFAULT_HTTP_READ_TIMEOUT_IN_SECONDS                = 30;
        private int DEFAULT_HTTP_WRITE_TIMEOUT_IN_SECONDS               = 15;

        private String DEFAULT_OK_HTTP_CACHE_FILE_NAME                  = "okHttpClientCache";
        private long DEFAULT_OK_HTTP_CACHE_SIZE                         = 10 * 1024 * 1024; /* 10 MB Cache size */

        private Context context;

        private boolean showLog                                         = false;

        private long connectTimeout                                     = DEFAULT_HTTP_CONNECT_TIMEOUT_IN_SECONDS;
        private long writeTimeout                                       = DEFAULT_HTTP_WRITE_TIMEOUT_IN_SECONDS;
        private long readTimeout                                        = DEFAULT_HTTP_READ_TIMEOUT_IN_SECONDS;

        private boolean enableCache                                     = false;
        private String cacheFileName                                    = DEFAULT_OK_HTTP_CACHE_FILE_NAME;
        private long cacheSize                                          = DEFAULT_OK_HTTP_CACHE_SIZE;

        private HashMap<String, String> headers                         = new HashMap<String, String>(); /* Request header */
        private String apiKey                                           = null;
        private String bearerAuthenticationToken                        = null;

        private ArrayList<Interceptor> interceptorArrayList             = new ArrayList<Interceptor>();
        private ArrayList<Interceptor> networkInterceptorArrayList      = new ArrayList<Interceptor>();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setShowLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder enableCache(boolean enableCache) {
            this.enableCache = enableCache;
            return this;
        }

        public Builder setCacheFileName(String cacheFileName) {
            this.cacheFileName = cacheFileName;
            return this;
        }

        public Builder setCacheSize(long cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        public Builder addHeader(HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder setBearerAuthenticationToken(String bearerAuthenticationToken) {
            this.bearerAuthenticationToken = bearerAuthenticationToken;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptorArrayList.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            networkInterceptorArrayList.add(interceptor);
            return this;
        }

        public OkHttpClient build(){
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);

            okHttpClientBuilder.retryOnConnectionFailure(true);

            if (headers != null && headers.size() > 0)
            {
                /*HashMap<String, String> headers = new HashMap<>();
                headers.put(HeaderInterceptor.HEADER_KEY_CONTENT_TYPE, "application/json; charset=utf-8");
                headers.put(HeaderInterceptor.HEADER_KEY_ACCEPT, "application/json");
                headers.put(HeaderInterceptor.HEADER_KEY_ACCEPT_CHARSET, "utf-8");
                headers.put(HeaderInterceptor.HEADER_KEY_USER_AGENT, "Android App");
                headers.put("app_version", "android_" + 1.0);*/

                okHttpClientBuilder.addInterceptor(new HeaderInterceptor(headers));
            }

            if(!isEmpty(apiKey))
            {
                okHttpClientBuilder.addInterceptor(new ApiKeyInterceptor(apiKey));
            }

            if(!isEmpty(bearerAuthenticationToken))
            {
                okHttpClientBuilder.addInterceptor(new BearerAuthenticationInterceptor(bearerAuthenticationToken));
            }

            if (enableCache)
            {
                Cache cache = new CacheCreator(context, cacheFileName, cacheSize).getCache();

                if (cache != null)
                {
                    okHttpClientBuilder.cache(cache);

                    /* used if network off OR on */
                    okHttpClientBuilder.addInterceptor(new ProvideOfflineCacheInterceptor(context));

                    /* only used when network is on */
                    okHttpClientBuilder.addNetworkInterceptor(new ProvideCacheInterceptor(context));
                }
            }

            /* used if network off OR on */
            if(interceptorArrayList.size() != 0)
            {
                for (Interceptor interceptor : interceptorArrayList) {
                    okHttpClientBuilder.addInterceptor(interceptor);
                }
            }

            /* only used when network is on */
            if(networkInterceptorArrayList.size() != 0)
            {
                for (Interceptor interceptor : networkInterceptorArrayList) {
                    okHttpClientBuilder.addNetworkInterceptor(interceptor);
                }
            }

            if (showLog)
            {
                Interceptor interceptor = new LoggingInterceptor.Builder().setLevel(Level.BASIC).log(VERBOSE).build();
                okHttpClientBuilder.addInterceptor(interceptor);
            }

            return okHttpClientBuilder.build();
        }
    }

    /**
     * is null or its length is 0
     *
     * isEmpty(null)        = true;
     * isEmpty("")          = true;
     * isEmpty("     ")     = false;
     * isEmpty("   Hello ") = false;
     * isEmpty("null")      = false;
     *
     * @param string
     * @return if string is null or its size is 0, return true, else return false.
     */
    public boolean isEmpty(String string) {
        return ((string == null) || ("null".equalsIgnoreCase(string) || (string.trim().length() == 0)));
    }
}
