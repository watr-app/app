/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.settings;

import android.content.SharedPreferences;

public class SettingsManager {

  /*
  Things to note:
  - no double || float sadly
  - the default return values are "There is no such thing" / 1 / false

  The following declaration is needed in main:

  private UserSharedPreferenceManager NAME_OF_YOUR_CHOICE = new UserSharedPreferenceManager();

  the SharedPreferences() method could perhaps later be implemented as a common method to save time from writing it to every method.
   */

  // First parameter is always getSharedPreferences("NAME_OF_PREFERENCES_FILE, MODE"), second one is
  // key name, third is value
  public void addString(SharedPreferences contextualSharedPreferences, String keyname, String value) {
    SharedPreferences.Editor prefEditor = contextualSharedPreferences.edit();
    prefEditor.putString(keyname, value);
    prefEditor.apply();
  }

  // same as string but requires number as value instead of a String
  public void addInt(SharedPreferences contextualSharedPreferences, String keyname, int value) {
    SharedPreferences.Editor prefEditor = contextualSharedPreferences.edit();
    prefEditor.putInt(keyname, value);
    prefEditor.apply();
  }

  // again requires a Boolean
  public void addBoolean(SharedPreferences contextualSharedPreferences, String keyname, boolean value) {
    SharedPreferences.Editor prefEditor = contextualSharedPreferences.edit();
    prefEditor.putBoolean(keyname, value);
    prefEditor.apply();
  }

  // Fetchers only require the shared preferences method and name of the preference
  public String getString(SharedPreferences contextualSharePreferences, String keyname) {
    return contextualSharePreferences.getString(keyname, "There is no such thing");
  }

  public int getInt(SharedPreferences contextualSharePreferences, String keyname) {
    return contextualSharePreferences.getInt(keyname, 0);
  }

  public boolean getBoolean(SharedPreferences contextualSharePreferences, String keyname) {
    return contextualSharePreferences.getBoolean(keyname, false);
  }
}
