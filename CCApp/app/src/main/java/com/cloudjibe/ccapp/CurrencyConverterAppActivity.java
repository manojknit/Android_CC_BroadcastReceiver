package com.cloudjibe.ccapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CurrencyConverterAppActivity extends AppCompatActivity {

    public static final String tag="CCApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fillCurrencySpinner();

        Intent intent = getIntent();
        if(intent.hasExtra("dollar")) {
            String dollar = intent.getExtras().getString("dollar");
            String currencyto = intent.getExtras().getString("currencyto");
            String currencyConverted = intent.getExtras().getString("currencyvalue");
            EditText etd=(EditText)findViewById(R.id.etDollar);
            Spinner spinner = (Spinner) findViewById(R.id.spnrCurrency);
            TextView tvo=(TextView)findViewById(R.id.tvCCOutput);
            etd.setText(dollar);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.currency_array, android.R.layout.simple_spinner_item);
            spinner.setSelection(adapter.getPosition(currencyto));
            tvo.setText("Dollar amount $"+dollar+" converted to " +currencyConverted+" "+currencyto);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        if(MyReceiver!= null)
//            unregisterReceiver(MyReceiver);;
    }

    private void fillCurrencySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spnrCurrency);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    public void onCancelClick(View view) {
        finishAffinity();
    }
    public void onGenerateClick(View view) {
        //EditText e=(EditText)findViewById(R.id.etSimulationCount);
        //int limit = Integer.parseInt(e.getText().toString());
        testSendBroadcast();

    }


    private void testSendBroadcast()
    {
        //Create an intent with an action
        final Intent broadcastIntent = new Intent("com.cloudjibe.ccapp.intents.exchange");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //load up the intent with a message
        //you want to broadcast
        EditText e=(EditText)findViewById(R.id.etDollar);
        String dollar = e.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spnrCurrency);
        //TextView e1=(TextView)findViewById(R.id.tvDollarValue);
        String currencytype = spinner.getSelectedItem().toString();
        broadcastIntent.putExtra("dollar", dollar);
        broadcastIntent.putExtra("currencyto", currencytype);

        broadcastIntent.setComponent(
                new ComponentName("com.cloudjibe.currencyexchange","com.cloudjibe.currencyexchange.ExchangeReceiver"));

        //send out the broadcast
        //there may be multiple receivers receiving it
        this.sendBroadcast(broadcastIntent);

        //Log a message after sending the broadcast
        //This message should appear first in the log file
        //before the log messages from the broadcast
        //because they all run on the same thread
        Log.d(tag,"------------after send broadcast from main menu");
    }
}
