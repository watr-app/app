/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences;

/**
 * Indicates an exception (I.e. a missing value or failure to set default value) in data store
 * pre-flight checks.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class DataStorePreFlightCheckException extends Exception {
  public DataStorePreFlightCheckException(String message) {
    super(message);
  }
}
