package com.library.networkconnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkStateChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkStateChangeReceiver.class.getSimpleName();
    public static NetworkStateChangeListener networkStateChangeListener;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        final String action = intent.getAction();

        if (action != null) {
            if (networkStateChangeListener != null) {

                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetworkConnectivityUtil.isConnectedAll(context))
                    {
                        Log.e(TAG,"ConnectivityManager.CONNECTIVITY_ACTION - 1");
                        networkStateChangeListener.networkAvailable();
                    }
                    else
                    {
                        Log.e(TAG,"ConnectivityManager.CONNECTIVITY_ACTION - 2");
                        networkStateChangeListener.networkUnavailable();
                    }
                }

                if (!intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false))
                {
                    Log.e(TAG,"RegisterAndUnregisterNetworkReceiver.NETWORK_AVAILABILITY_ACTION - 1");
                    networkStateChangeListener.networkAvailable();
                }
                else
                {
                    Log.e(TAG,"RegisterAndUnregisterNetworkReceiver.NETWORK_AVAILABILITY_ACTION - 2");
                    networkStateChangeListener.networkUnavailable();
                }
            }
        }
    }

    public static void setNetworkStateChangeListener(NetworkStateChangeListener listener) {
        NetworkStateChangeReceiver.networkStateChangeListener = listener;
    }
}