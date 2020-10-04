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
  private static final double FLUID_OUNCE = 28.41306;

  /**
   * Normalise a percentage to not exceed a maximum value (Usually 100%).
   *
   * @param current {@link Integer}
   * @param max {@link Integer}
   * @return {@link Integer} Percentage normalised to not exceed provided ceiling
   */
  public static double normalisePercentage(double current, double max) {
    return Math.min(current, max);
  }

  public static int doubleToInteger (double value) {
    return (int) Math.round(value);
  }

  /**
   * Convert millilitres into imperial fluid ounces. The definition of "fluid ounce" is murky at
   * best, but this conversion uses the common "imperial" measurement instead of the "US customary"
   * one.
   *
   * <p>Note: Millilitre values provided as {@link Integer}s are coerced to {@link Double}s to
   * preserve calculation accuracy; the return value of the method also reflects this. Thusly, any
   * roundings to create {@link Integer}s are to be done at display time.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Fluid_ounce>Fluid ounce - Wikipedia</a>
   * @param millilitres {@link Integer}
   * @return {@link Double} Provided millilitre amount in fluid ounces.
   */
  public static double convertMillilitresToFluidOunces(int millilitres) {
    return ((double) millilitres) / FLUID_OUNCE;
  }
}
