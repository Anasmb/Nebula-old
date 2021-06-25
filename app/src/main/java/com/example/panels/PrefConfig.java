package com.example.panels;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {

    private static final String APP_PREF = "com.example.panels";
    private static final String PREF_ADDRESS = "pref_address";

    public static void saveIP(Context context , String ipAddress){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREF , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_ADDRESS, ipAddress);
        editor.apply();
    }

    public static String loadIP(Context context){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREF , Context.MODE_PRIVATE);
        return preferences.getString(PREF_ADDRESS , "");
    }

    public static void clearIP(Context context){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREF,Context.MODE_PRIVATE);
        preferences.edit().remove(PREF_ADDRESS).commit();
    }


}
