package com.pop.popularmovies.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pop.popularmovies.MainFragment;

/**
 * Created by Pop H2 on 8/3/2016.
 * Pop Inc
 * Lagos Nigeria
 */
public class InternetIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        Intent i = new Intent(MainFragment.LOAD_MOVIES_INTENT);
        context.sendBroadcast(i);


    }
}
