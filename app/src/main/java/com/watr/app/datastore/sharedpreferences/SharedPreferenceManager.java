/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.datastore.sharedpreferences;

import android.content.SharedPreferences;
import com.watr.app.constants.SharedPreferenceType;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Parent class for manager classes that deal with SharedPreferences. Abstracts away some common
 * functions like editing and applying settings from subclasses.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@RequiredArgsConstructor
public abstract class SharedPreferenceManager {
  private final SharedPreferences ctx;

  /**
   * Edit and apply a change to the provided SharedPreferences instance.
   *
   * @param type {@link SharedPreferenceType}
   * @param key {@link String}
   * @param value {@link Object}
   * @throws {@link ClassCastException} if the type coercion failed.
   */
  // Suppressing the typecast warning here because we cannot know the type before runtime and the
  // Set<String> coercion will complain otherwise
  @SuppressWarnings("unchecked")
  protected void editAndApply(SharedPreferenceType type, String key, Object value) throws ClassCastException {
    val editor = ctx.edit();

    switch (type) {
      case BOOLEAN:
        editor.putBoolean(key, (boolean) value);
        break;
      case FLOAT:
        editor.putFloat(key, (float) value);
        break;
      case INTEGER:
        editor.putInt(key, (int) value);
        break;
      case LONG:
        editor.putLong(key, (long) value);
        break;
      case STRING:
        editor.putString(key, (String) value);
        break;
      case STRINGSET:
        editor.putStringSet(key, (Set<String>) value);
        break;
    }

    editor.apply();
  }
}
