package com.samuelbernard147.smartvillage.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.samuelbernard147.smartvillage.Helper.NotifHelper;

public class SettingsPreference {
    private static final String PREFS_NAME = "settings_pref";

    private static final String LANG = "lang";
    private static final String PANIC = "panic";
    private static final String FLOOD = "flood";
    private static final String FIRE = "fire";

    private final SharedPreferences preferences;
    private final NotifHelper helper;

    public SettingsPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        helper = new NotifHelper(context);
    }

    public void setPanic(Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PANIC, value);
        editor.apply();
        if (value) {
            helper.startDispatcherPanic();
        } else {
            helper.cancelDispatcher(helper.PANIC_TAG);
        }
    }

    public void setFlood(Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FLOOD, value);
        editor.apply();
        if (value) {
            helper.startDispatcherFlood();
        } else {
            helper.cancelDispatcher(helper.FLOOD_TAG);
        }
    }

    public void setFire(Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRE, value);
        editor.apply();
        if (value) {
            helper.startDispatcherFire();
        } else {
            helper.cancelDispatcher(helper.FIRE_TAG);
        }
    }

    public void setLang(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANG, value);
        editor.apply();
    }

    public String getLang() {
        return preferences.getString(LANG, "en");
    }

    public boolean getPanic() {
        return preferences.getBoolean(PANIC, true);
    }

    public boolean getFlood() {
        return preferences.getBoolean(FLOOD, true);
    }

    public boolean getFire() {
        return preferences.getBoolean(FIRE, true);
    }
}