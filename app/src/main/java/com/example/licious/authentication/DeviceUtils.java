package com.example.licious.authentication;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtils {
    public static String getDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
}
