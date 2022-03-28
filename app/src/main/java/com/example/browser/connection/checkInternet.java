package com.example.browser.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class checkInternet extends BroadcastReceiver {
//    ConnectionChangeCallback connectionChangeCallback;

    @Override
    public void onReceive(Context context, Intent intent) {

//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] activeNetwork = cm.getAllNetworkInfo();
//        boolean isConnected = activeNetwork != null;

        String status= null;
        status=getNetworkInfo(context);
//        Boolean heh=internetIsConnected();
        Log.i("inreci", "onReceive: ");
        if(status.equals("connected") ){
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();}
//        }
//        if(status.equals("connected") && heh){
//            Toast.makeText(context, "connected and has internet", Toast.LENGTH_SHORT).show();
//        }
//        else if(status.equals("connected") && (!heh)){
//            Toast.makeText(context, "connected and has no internet", Toast.LENGTH_SHORT).show();
//        }
        else if(status.equals("disconnected") ){
            Toast.makeText(context, "no internet connection", Toast.LENGTH_SHORT).show();
        }
//        boolean isConnected=status.equals("connected") && heh;
//        if (connectionChangeCallback != null) {
//            connectionChangeCallback.onConnectionChange(isConnected);
//        }

    }

    public static String getNetworkInfo(Context context){
        String status=null;
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] networkInfo= connectivityManager.getAllNetworkInfo();

        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
//        boolean jojo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        if(networkInfo!=null){
            status="connected";
            return status;
        }
        else {
            status="disconnected";
            return status;
        }
    }
//    public static boolean internetIsConnected() {
//        try {
//            String command = "ping -c 1 google.com";
//            return (Runtime.getRuntime().exec(command).waitFor() == 0);
//        } catch (Exception e) {
//            return false;
//        }
//    }

//    public void setConnectionChangeCallback(ConnectionChangeCallback
//                                                    connectionChangeCallback) {
//        this.connectionChangeCallback = connectionChangeCallback;
//    }
//
//
//    public interface ConnectionChangeCallback {
//
//        void onConnectionChange(boolean isConnected);
//
//    }


}

