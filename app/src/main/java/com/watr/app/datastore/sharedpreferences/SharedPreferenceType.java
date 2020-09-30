/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences;

/**
 * Record of supported types for SharedPreferences.
 *
 * @see <a href="https://developer.android.com/reference/android/content/SharedPreferences>SharedPreferences</a>
 * @author linuswillner
 * @version 1.0.0
 */
public enum SharedPreferenceType {
  BOOLEAN,
  FLOAT,
  INTEGER,
  LONG,
  STRING,
  STRINGSET
}
