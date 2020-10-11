/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.utils;

import android.widget.EditText;
import lombok.val;

/**
 * Miscellaneous input utilities and helpers.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class InputUtils {

  /**
   * Checks if an {@link EditText} field value is empty.
   *
   * @param inputField {@link EditText}
   * @return Whether input field value was empty
   */
  public static boolean inputIsEmpty (EditText inputField) {
    val input = inputField.getText();
    return input == null || input.toString().equals("");
  }

  /**
   * Checks if an {@link EditText} field value is empty or zero.
   *
   * @param inputField {@link EditText}
   * @return Whether input field value was empty or zero
   */
  public static boolean inputIsEmptyOrZero (EditText inputField) {
    val input = inputField.getText();
    return input == null || input.toString().equals("") || Integer.parseInt(input.toString()) == 0;
  }

  /**
   * Parses and returns an integer value from an EditText input.
   *
   * @param inputField {@link EditText}
   * @return {@link Integer}
   */
  public static int getInteger (EditText inputField) {
    return Integer.parseInt(inputField.getText().toString());
  }
}
