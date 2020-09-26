/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.sharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;


public class sharedPreferences {
  private static final String SHARED_PREFS = "sharedPrefs";
  private static String NAME = "name";
  private static String WEIGHT = "weight";


public void saveData() {
  SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
  SharedPreferences.Editor editor = sharedPreferences.edit();

  editor.putString(NAME, person.getName());
  editor.putInt(WEIGHT, person.getAge());
  editor.apply();
  }

public void loadData() {
  SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
  Person person = new Person(
      sharedPreferences.getString(NAME, ""),
      sharedPreferences.getInt(WEIGHT, -1));

  return person;
  }
}
