/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences;

public class SharedPreferenceNotFoundException extends Exception {
  public SharedPreferenceNotFoundException (String message) {
    super(message);
  }
}
