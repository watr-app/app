/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.userprofile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

public class UserProfileManager {

  private static final String SHARED_PREFS = "sharedPrefs";
  private static final String SEX = "sex";
  private static final String WEIGHT = "weight";

  public void saveData(boolean sex, int weight) {
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.apply();
  }

  Boolean sex = sharedPreferences.getBoolean(SEX, false);

}
