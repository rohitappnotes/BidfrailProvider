package com.library.sharedpreferences;

import java.util.Map;
import java.util.Set;

public interface Builder {

    public void putBoolean(final String key, final Boolean newValue);
    public boolean getBoolean(final String key);
    public boolean getBoolean(final String key, final Boolean defaultValue);

    public void putString(final String key, final String newValue);
    public String getString(final String key);
    public String getString(final String key, final String defaultValue);

    public void putInt(final String key, final int newValue);
    public int getInt(final String key);
    public int getInt(final String key, final int defaultValue);

    public void putLong(final String key, final long newValue);
    public long getLong(final String key);
    public long getLong(final String key, final long defaultValue);

    public void putFloat(final String key, final float newValue);
    public float getFloat(final String key);
    public float getFloat(final String key, final float defaultValue);

    public void putDouble(final String key, final double newValue);
    public double getDouble(final String key);
    public double getDouble(final String key, final double defaultValue);

    public void putStringSet(final String key, final Set<String> newValue);
    public Set<String> getStringSet(final String key);
    public Set<String> getStringSet(final String key, final Set<String> defaultValue);

    public Map<String, ?> getAll();

    public boolean containsKey(final String key);
    public void removeKey(final String key);

    public void clearSharedPreferences();
}
