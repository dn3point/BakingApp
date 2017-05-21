package com.iamzhaoyuan.android.baking.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    private static NetworkUtils instance = null;

    private NetworkUtils() {

    }

    public static synchronized NetworkUtils getInstance() {
        if (instance == null) instance = new NetworkUtils();
        return instance;
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = false;
        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        }
        return isConnected;
    }
}
