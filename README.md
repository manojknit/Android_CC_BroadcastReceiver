# Android app to demonstrate Broadcast Receiver behavior of android
####                                                                                                     By Manoj Kumar
## Introduction 
Android app to demonstrate Broadcast Receiver behavior of android.

Two apps
Currency Converter Request App:
The purpose of the app is to request currency conversion from USD to other currencies: British Pound ( 1 USD  = 0.71 British Pound), Euro (1 USD = 0.81 Euro), INR (1 USD = 64.39 INR) - currency conversions as per 02/17/2018 exchange rates.

Currency Exchange Central App: 
Clicking on "Apply" button should use appropriate exchange rates:

## How to Run
1.	Prerequisite: Android Studio, some Java knowledge
2.	Download or clone project code and open in Android studio.
3.	Run in Nexus 5X API 26 emulator.


## Technologies Used
1.	Java.
2.	Android Studio


## Application Code and Screenshots (2 Apps)
#### Currency Converter Request App:
This app is to request currency conversion from USD to other currencies: British Pound ( 1 USD  = 0.71 British Pound), Euro (1 USD = 0.81 Euro), INR (1 USD = 64.39 INR) - currency conversions as per 02/17/2018 exchange rates.
<img src="images/Android Emulator - Nexus_5X_API_265554 2018-03-02 23-19-37.png">
Once converted
<img src="images/Android Emulator - Nexus_5X_API_265554 2018-03-02 23-20-00.png">
## Code: CurrencyConverterAppActivity
```
    private void sendBroadcastToExchange()
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
Strings.xml    ----
<resources>
    <string name="app_name">Currency Converter App</string>
    <string name="action_settings">Settings</string>
    <string-array name="currency_array">
        <item>British Pound</item>
        <item>Euro</item>
        <item>Indian Rupee</item>
    </string-array>
</resources>

```

#### Currency Exchange Central App.
Clicking on "Apply" button should use appropriate exchange rates.
<img src="images/Android Emulator - Nexus_5X_API_265554 2018-03-02 23-19-50.png">

## Code: ExchangeActivity
```
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
```
## Refrence
- [*Pro Android 5*](https://github.com/Apress/pro-android-5) by Dave MacLean, Satya Komatineni, and Grant Allen (Apress, 2015)

## Thank You
#### [*Manoj Kumar*](https://www.linkedin.com/in/manojkumar19/)
#### Solutions Architect
