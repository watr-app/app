/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.sharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.widget.TextView;


public class sharedPreferences {
  private static final String SHARED_PREFS = "sharedPrefs";
  private static final String SEX = "sex";
  private static final String WEIGHT = "weight";



  public void saveData(Boolean sex, int weight) {
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putBoolean(SEX, sex);
    editor.putInt(WEIGHT, weight);
    editor.apply();
  }

  public void loadData() {
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    sharedPreferences.getString(SEX, "");
    sharedPreferences.getInt(WEIGHT, -1);
  }
}