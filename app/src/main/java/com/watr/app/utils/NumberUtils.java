/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.utils;

/**
 * Miscellaneous number utilities and helpers.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class NumberUtils {
  public static int normalisePercentage (int current, int max) {
    return Math.min(current, max);
  }
}
