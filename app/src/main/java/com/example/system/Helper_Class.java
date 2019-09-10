package com.example.system;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Helper_Class
{

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    public static boolean connectionAvailable(Context context)
    {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
        { //Connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                connected = true;
        }
        return connected;
    }




}
