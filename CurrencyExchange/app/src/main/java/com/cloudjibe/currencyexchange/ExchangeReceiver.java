package com.cloudjibe.currencyexchange;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ExchangeReceiver extends BroadcastReceiver
{
	private static final String tag = "ExchangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {

        Log.d(tag, "Exchange Receiver called. --Manoj");
        Toast.makeText(context, "Exchange Receiver Triggered"     , Toast.LENGTH_LONG).show();
        Log.d(tag, "intent=" + intent);
        String dollar = intent.getExtras().getString("dollar");
        String currencyto = intent.getStringExtra("currencyto");
        Toast.makeText(context, "Exchange Receiver Triggered"+dollar+","+ currencyto    , Toast.LENGTH_LONG).show();

        PackageManager packageManager = context.getPackageManager();

        Intent intentReceiverActivity = packageManager.getLaunchIntentForPackage("com.cloudjibe.currencyexchange");
        intentReceiverActivity.putExtra("currencyto", currencyto);
        intentReceiverActivity.putExtra("dollar", dollar);
        context.startActivity(intentReceiverActivity);
        Log.d(tag, dollar+", "+currencyto);
    }
}

