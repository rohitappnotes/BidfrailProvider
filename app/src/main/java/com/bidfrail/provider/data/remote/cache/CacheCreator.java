package com.bidfrail.provider.data.remote.cache;

import android.content.Context;
import android.util.Log;
import java.io.File;
import okhttp3.Cache;

public class CacheCreator {

    public static final String TAG = CacheCreator.class.getSimpleName();

    private Context context;

    private String cacheFileName; /* inside above folder cache file name */
    private long cacheSize; /* cache size */

    private Cache cache;
    private File cacheDirectory;

    public CacheCreator(Context context, String cacheFileName, long cacheSize) {
        this.context            = context;
        this.cacheFileName      = cacheFileName;
        this.cacheSize          = cacheSize;
    }

    public Cache getCache() {
        setCache();
        return cache;
    }

    private void setCache() {
        if (cacheDirectory == null) {
            cacheDirectory = new File(context.getCacheDir(), cacheFileName);
        }
        try
        {
            if (cache == null)
            {
                cache = new Cache(cacheDirectory, cacheSize);
            }
        }
        catch (Exception exception)
        {
            Log.e(TAG, "Could not create Cache : "+exception.getMessage());
        }
    }
}
