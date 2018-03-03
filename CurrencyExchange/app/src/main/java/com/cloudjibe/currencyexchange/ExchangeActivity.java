package com.cloudjibe.currencyexchange;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ExchangeActivity extends AppCompatActivity {
    public static final String tag="AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent.hasExtra("dollar")) {
            String dollar = intent.getExtras().getString("dollar");
            String currencyto = intent.getExtras().getString("currencyto");
            TextView tvd = (TextView) findViewById(R.id.tvDollarValue);//getTextView();
            TextView tvc = (TextView) findViewById(R.id.tvConvertToValue);
            tvd.setText(dollar);
            tvc.setText(currencyto);
        }
    }



    public void onApplyClick(View view) {

        appliedConversionSendBroadcast();
    }

    public void onCloseClick(View view) {
        finishAffinity();
    }
    private void appliedConversionSendBroadcast()
    {
        //Create an intent with an action
        final Intent broadcastIntent = new Intent("com.cloudjibe.ccapp.intents.ccapp");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //load up the intent with a message

        TextView e=(TextView)findViewById(R.id.tvDollarValue);
        int dollar = Integer.parseInt(e.getText().toString());
        TextView e1=(TextView)findViewById(R.id.tvConvertToValue);
        String currencytype = e1.getText().toString();
        double currval = 0;
        switch (currencytype)
        {
            case "British Pound":
                currval = 0.71*dollar;// as of 17th Feb 2018
                break;
            case "Euro":
                currval = 0.81*dollar;
                break;
            case "Indian Rupee":
                currval = 64.39*dollar;
                break;
            default:
                currval = 1*dollar;
        }

        //you want to broadcast
        broadcastIntent.putExtra("dollar", Integer.toString(dollar));
        broadcastIntent.putExtra("currencyto", currencytype);
        broadcastIntent.putExtra("currencyvalue", Double.toString(currval));

        broadcastIntent.setComponent(
                new ComponentName("com.cloudjibe.ccapp","com.cloudjibe.ccapp.CurrencyConverterReceiver"));

        //send out the broadcast
        //there may be multiple receivers receiving it
        this.sendBroadcast(broadcastIntent);

        //Log a message after sending the broadcast
        //This message should appear first in the log file
        //before the log messages from the broadcast
        //because they all run on the same thread
        Log.d(tag,"------------after send broadcast from Exchange Activity");
    }

}
