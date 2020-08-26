package com.virus.covid19.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.virus.covid19.BuildConfig;

import java.lang.reflect.Field;

public final class DeviceUtility {


    private static final String TAG = DeviceUtility.class.getSimpleName();

    public DeviceUtility() {

    }

    @SuppressWarnings("SameReturnValue")
    public static String getDefaultEncoding() {
        return "UTF-8";
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getDpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public static int getPxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float convertPixelsToDp(Context context,float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @SuppressWarnings("SameReturnValue")
    public static int getOsVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getOsName() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = "";
        if (fields.length > Build.VERSION.SDK_INT+1) {

            osName = fields[Build.VERSION.SDK_INT + 1].getName();
        } else {
            osName = fields[Build.VERSION.SDK_INT].getName();

        }
        return osName;

    }

    @SuppressWarnings("SameReturnValue")
    public static String getBrandName() {
        return Build.BRAND;

    }

    @SuppressWarnings("SameReturnValue")
    public static String getModelName() {
        return Build.MODEL;
    }

    @SuppressWarnings("SameReturnValue")
    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

  /*  public static String getCountryCode(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();

    }

    public static String getCountry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();

    }*/


    public static String getISOCountryCode(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimCountryIso().trim();
    }



}
