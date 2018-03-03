package com.cloudjibe.ccapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class CurrencyConverterReceiver extends BroadcastReceiver
{
	private static final String tag = "CCReceiver";
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Log.d(tag, "intent=" + intent);
        Log.d(tag, "CCApp received converted currency from exchange.");
       // Toast.makeText(context, "CC Receiver Triggered"     , Toast.LENGTH_LONG).show();

        String dollar = intent.getExtras().getString("dollar");
        String currencyto = intent.getExtras().getString("currencyto");
        String currencyvalue = intent.getExtras().getString("currencyvalue");

        Toast.makeText(context, "Exchange Receiver Triggered $="+dollar+", currencyvalue="+ currencyvalue    , Toast.LENGTH_LONG).show();
        PackageManager packageManager = context.getPackageManager();
        Intent intentReceiverActivity = packageManager.getLaunchIntentForPackage("com.cloudjibe.ccapp");
        intentReceiverActivity.putExtra("dollar", dollar);
        intentReceiverActivity.putExtra("currencyto", currencyto);
        intentReceiverActivity.putExtra("currencyvalue", currencyvalue);
        context.startActivity(intentReceiverActivity);
        Log.d(tag, currencyvalue+", "+currencyvalue);
    }
}

