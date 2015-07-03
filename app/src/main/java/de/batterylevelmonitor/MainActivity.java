package de.batterylevelmonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private BroadcastReceiver batChReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.this.onBatteryChangeBroadcastReceived(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final IntentFilter batChFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        //final LowBatteryReceiver batChReceiver = new LowBatteryReceiver();

        final IntentFilter batChFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        this.registerReceiver(batChReceiver, batChFilter);
    }

    //Kein Ladegerät
    //./adb shell am broadcast -a android.intent.action.BATTERY_CHANGED --ei plugged 0
    //AC-Ladegerät
    //./adb shell am broadcast -a android.intent.action.BATTERY_CHANGED --ei plugged 1
    //USB-Ladegerät
    //./adb shell am broadcast -a android.intent.action.BATTERY_CHANGED --ei plugged 2

    private void onBatteryChangeBroadcastReceived(Intent batteryChangedIntent)
    {
        TextView textView = (TextView) findViewById(R.id.textView);
        int newBackgroundColor;
        final View topLevelContainer = this.findViewById(R.id.topLevelContainer);

        switch (batteryChangedIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0))
        {
            case 1:
                textView.setText("AC-Ladegerät angeschlossen");
                newBackgroundColor = Color.rgb(0,255, 0);
                break;

            case 2:
                textView.setText("USB-Ladegerät angeschlossen");
                newBackgroundColor = Color.rgb(0,255, 0);
                break;

            default:
                textView.setText("Kein Ladegerät angeschlossen");
                newBackgroundColor = Color.rgb(255,0, 0);
                break;
        }

        topLevelContainer.setBackgroundColor(newBackgroundColor);

    }


    @Override protected void onResume() {
        super.onResume();
        final IntentFilter batChFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final Intent currBattChangeIntent = this.registerReceiver(this.batChReceiver, batChFilter);
        this.onBatteryChangeBroadcastReceived(currBattChangeIntent);
    }
    @Override protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.batChReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
