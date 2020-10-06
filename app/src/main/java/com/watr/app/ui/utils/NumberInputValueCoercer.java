/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.utils;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * {@link TextWatcher} implementation to force {@link EditText} inputs to be within a certain
 * numerical range or else get truncated.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class NumberInputValueCoercer implements TextWatcher {
  private final EditText inputField;
  private final int min;
  private final int max;

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {}

  @SuppressLint("SetTextI18n")
  @Override
  public void afterTextChanged(Editable s) {
    val input = inputField.getText();

    if (input != null && !input.toString().equals("")) {
      val number = Integer.parseInt(input.toString());

      if (number > max) {
        inputField.setText(Integer.toString(max));
      } else if (number < min) {
        inputField.setText(Integer.toString(min));
      }
    }
  }
}
