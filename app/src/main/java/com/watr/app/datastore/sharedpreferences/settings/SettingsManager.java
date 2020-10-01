/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences.settings;

import android.content.SharedPreferences;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceManager;
import com.watr.app.constants.SharedPreferenceType;
import java.util.Set;
import lombok.Getter;

/**
 * User settings manager leveraging SharedPreferences. All methods in this class modify the provided
 * SharedPreferences instance.
 *
 * @author panueronen
 * @version 1.0.0
 */
public class SettingsManager extends SharedPreferenceManager {
  @Getter private final SharedPreferences ctx;

  public SettingsManager(SharedPreferences ctx) {
    super(ctx);
    this.ctx = ctx;
  }

  /**
   * Sets a string value.
   *
   * @param key
   * @param value
   */
  public void addString(String key, String value) {
    super.editAndApply(SharedPreferenceType.STRING, key, value);
  }

  /**
   * Sets a (32-bit) integer value.
   *
   * @param key
   * @param value
   */
  public void addInt(String key, int value) {
    super.editAndApply(SharedPreferenceType.INTEGER, key, value);
  }

  /**
   * Sets a boolean value.
   *
   * @param key
   * @param value
   */
  public void addBoolean(String key, boolean value) {
    super.editAndApply(SharedPreferenceType.BOOLEAN, key, value);
  }

  /**
   * Sets a floating-point value.
   *
   * @param key
   * @param value
   */
  public void addFloat(String key, float value) {
    super.editAndApply(SharedPreferenceType.FLOAT, key, value);
  }

  /**
   * Sets a long (64-bit integer) value.
   *
   * @param key
   * @param value
   */
  public void addLong(String key, long value) {
    super.editAndApply(SharedPreferenceType.LONG, key, value);
  }

  /**
   * Sets a string set value.
   *
   * @param key
   * @param value
   */
  public void addStringSet(String key, Set<String> value) {
    super.editAndApply(SharedPreferenceType.STRINGSET, key, value);
  }
}
