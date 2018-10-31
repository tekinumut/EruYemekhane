package com.Mtkn.umutt.eruyemekhane.abstracts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class ConnectivityStatus {

    public static boolean isConnected(Context mContext) //İnternet varsa true, yoksa false dön
    {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

}
