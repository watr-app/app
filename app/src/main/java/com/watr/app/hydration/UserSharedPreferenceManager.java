/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.hydration;

import android.content.SharedPreferences;

public class UserSharedPreferenceManager {

  /*
  Things to note.
  no double || float sadly
  the default return values are "There is no such thing" , 1 , false
  Funktion names are long as poop for now but changeable later

  the following decleration is needed in main

  private UserSharedPreferenceManager NAME_OF_YOUR_CHOICE = new UserSharedPreferenceManager();

  the SharedPreferences() method could perhaps later be implemented as a common method to save time from writing it to every method.
   */

  //First parameter is always getSharedPreferences("NAME_OF_PREFERENCES_FILE, MODE"), second one is keyname, third is value
  public void UserSharedPreferenceManagerAddString(SharedPreferences contextualSharedPreferences,String keyname,String value ){
    SharedPreferences prefPut= contextualSharedPreferences;
    SharedPreferences.Editor prefEditor = prefPut.edit();
    prefEditor.putString(keyname, value);
    prefEditor.commit();
  }
  //same as string but requires number as value instead of a String
  public void UserSharedPreferenceManagerAddInt(SharedPreferences contextualSharedPreferences,String keyname, int value){
    SharedPreferences prefPut= contextualSharedPreferences;
    SharedPreferences.Editor prefEditor = prefPut.edit();
    prefEditor.putInt(keyname, value);
    prefEditor.commit();
  }
  //again requires a Boolean
  public void UserSharedPreferenceManagerAddBoolean(SharedPreferences contextualSharedPreferences,String keyname,boolean value){
    SharedPreferences prefPut= contextualSharedPreferences;
    SharedPreferences.Editor prefEditor = prefPut.edit();
    prefEditor.putBoolean(keyname, value);
    prefEditor.commit();
  }

  //Fetchers only require the shared preferences method and name of the preference
  public String UserSharedPreferenceManagerGetString(SharedPreferences contextualSharePreferences,String keyname){
    String returnvalue;
    SharedPreferences prefGet = contextualSharePreferences;
    returnvalue = prefGet.getString(keyname,"There is no such thing");
    return returnvalue;
  }
  public Integer UserSharedPreferenceManagerGetInt(SharedPreferences contextualSharePreferences,String keyname){
    int returnvalue;
    SharedPreferences prefGet = contextualSharePreferences;
    returnvalue = prefGet.getInt(keyname,0);
    return returnvalue;
  }
  public boolean UserSharedPreferenceManagerGetBoolean(SharedPreferences contextualSharePreferences,String keyname){
    boolean returnvalue;
    SharedPreferences prefGet = contextualSharePreferences;
    returnvalue = prefGet.getBoolean(keyname,false);
    return returnvalue;
  }
}
