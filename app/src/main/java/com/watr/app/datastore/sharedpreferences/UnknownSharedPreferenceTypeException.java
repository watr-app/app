/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.datastore.sharedpreferences;

/**
 * Thrown when the provided shared preference type did not match any of those found in {@link
 * com.watr.app.constants.SharedPreferenceType}.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class UnknownSharedPreferenceTypeException extends Exception {
  public UnknownSharedPreferenceTypeException(String message) {
    super(message);
  }
}
