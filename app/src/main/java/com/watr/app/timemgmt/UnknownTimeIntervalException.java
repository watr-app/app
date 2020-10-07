/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.timemgmt;

/**
 * Thrown when a time interval is unknown (Not wake => bed or bed => wake).
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class UnknownTimeIntervalException extends Exception {
  public UnknownTimeIntervalException(String message) {
    super(message);
  }
}
