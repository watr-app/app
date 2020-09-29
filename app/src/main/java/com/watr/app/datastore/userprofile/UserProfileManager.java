/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.userprofile;
// import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import com.watr.app.hydration.Gender;


public class UserProfileManager {



  // sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
  public void addSex(SharedPreferences sharedPreferences, String key, int sex) {

    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
    prefEditor.putInt(key, sex);
    prefEditor.apply();
  }

  public int getSex(SharedPreferences sharedPreferences, String key) {

    return sharedPreferences.getInt(key, 0);
  }
}
