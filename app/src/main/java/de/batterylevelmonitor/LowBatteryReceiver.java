package de.batterylevelmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Sebi on 03.07.15.
 */
public class LowBatteryReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        final String message = context.getString(R.string.lowBatteryMessage);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        //ADB Befehl:
        //./adb shell am broadcast -a android.intent.action.BATTERY_LOW

    }
}
