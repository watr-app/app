/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.utils;

import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Utility class to easily enable and disable buttons in a user-friendly way by additionally
 * altering their opacity in addition to altering their behaviour.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class ButtonStateToggler {
  private static final float OPAQUE = 1.0f;
  private static final float TRANSPARENT = 0.3f;

  /**
   * Enables a button and makes it opaque.
   *
   * @param button {@link Button}
   */
  public static void enableButton(Button button) {
    button.setAlpha(OPAQUE);
    button.setEnabled(true);
  }

  /**
   * Enables a button and makes it opaque.
   *
   * @param button {@link FloatingActionButton}
   */
  public static void enableButton(FloatingActionButton button) {
    button.setAlpha(OPAQUE);
    button.setEnabled(true);
  }

  /**
   * Disables a button and increases its transparency.
   *
   * @param button {@link Button}
   */
  public static void disableButton(Button button) {
    button.setAlpha(TRANSPARENT);
    button.setEnabled(false);
  }

  /**
   * Disables a button and increases its transparency.
   *
   * @param button {@link FloatingActionButton}
   */
  public static void disableButton(FloatingActionButton button) {
    button.setAlpha(TRANSPARENT);
    button.setEnabled(false);
  }
}
