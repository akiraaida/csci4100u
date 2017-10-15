package ca.uoit.csci4100u.lab04;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;

public class BatteryReceiver extends BroadcastReceiver {

    private static final int BATTERY_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = "";

        int chargeStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (chargeStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
            message += context.getString(R.string.battery_charging);
        } else if (chargeStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            message += context.getString(R.string.battery_discharging);
        } else if (chargeStatus == BatteryManager.BATTERY_STATUS_FULL) {
            message += context.getString(R.string.battery_full);
        }
        message += "\n";

        int healthStatus = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        if (healthStatus == BatteryManager.BATTERY_HEALTH_COLD) {
            message += context.getString(R.string.health_cold);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_DEAD) {
            message += context.getString(R.string.health_dead);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_GOOD) {
            message += context.getString(R.string.health_good);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
            message += context.getString(R.string.health_over_voltage);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
            message += context.getString(R.string.health_overheating);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
            message += context.getString(R.string.health_unknown);
        } else if (healthStatus == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
            message += context.getString(R.string.health_failed);
        }
        message += "\n";

        int pluggedStatus = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if (pluggedStatus == BatteryManager.BATTERY_PLUGGED_USB) {
            message += context.getString(R.string.plugged_usb);
        } else if (pluggedStatus == BatteryManager.BATTERY_PLUGGED_AC) {
            message += context.getString(R.string.plugged_ac);
        } else if (pluggedStatus == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
            message += context.getString(R.string.plugged_wireless);
        }
        message += "\n";

        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        message += context.getString(R.string.battery_temp) + " " + temperature;
        message += "\n";

        displayNotification(context, message);
    }

    public void displayNotification(Context context, String message) {
        int icon = R.drawable.ic_stat_name;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(icon);
        builder.setContentTitle(context.getString(R.string.battery_status));
        builder.setContentText(context.getString(R.string.battery_status));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        NotificationManager notificationManager;
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(BATTERY_ID, builder.build());
    }
}
