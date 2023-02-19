package com.library.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SharedPreferenceBuilder implements Builder {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor sharedPreferencesEditor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferenceBuilder(Context context, String sharedPreferencesFileName) {
        sharedPreferences = context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getSharedPreferencesEditor() {
        return sharedPreferencesEditor;
    }

    @Override
    public void putBoolean(String key, Boolean newValue) {
        getSharedPreferencesEditor().putBoolean(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, Boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    @Override
    public void putString(String key, String newValue) {
        getSharedPreferencesEditor().putString(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    @Override
    public void putInt(String key, int newValue) {
        getSharedPreferencesEditor().putInt(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    @Override
    public void putLong(String key, long newValue) {
        getSharedPreferencesEditor().putLong(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public long getLong(String key) {
        return getSharedPreferences().getLong(key, 0L);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    @Override
    public void putFloat(String key, float newValue) {
        getSharedPreferencesEditor().putFloat(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public float getFloat(String key) {
        return getSharedPreferences().getFloat(key, 0.0f);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    @Override
    public void putDouble(String key, double newValue) {
        getSharedPreferencesEditor().putString(key, String.valueOf(newValue));
        getSharedPreferencesEditor().apply();
    }

    @Override
    public double getDouble(String key) {
        String value = getSharedPreferences().getString(key, "0.0d");
        return Double.parseDouble(value);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(getSharedPreferences().getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    @Override
    public void putStringSet(String key, Set<String> newValue) {
        getSharedPreferencesEditor().putStringSet(key, newValue);
        getSharedPreferencesEditor().apply();
    }

    @Override
    public Set<String> getStringSet(String key) {
        Set<String> set = new HashSet<>();
        return getSharedPreferences().getStringSet(key, set);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getSharedPreferences().getStringSet(key, defaultValue);
    }

    @Override
    public Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    /**
     * Check key is present or not in SharedPreferences
     */
    @Override
    public boolean containsKey(String key) {
        return getSharedPreferences().contains(key);
    }

    @Override
    public void removeKey(String key) {
        getSharedPreferencesEditor().remove(key);
        getSharedPreferencesEditor().apply();
    }

    /**
     * Clears all data in SharedPreferences
     */
    @Override
    public void clearSharedPreferences() {
        getSharedPreferencesEditor().clear();
        getSharedPreferencesEditor().apply();
    }
}