/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Constants for biological genders and their IDs.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@RequiredArgsConstructor
public enum Gender {
  MALE(0, 3700),
  FEMALE(1, 2700);

  @Getter private final int genderId;
  @Getter private final int defaultDailyTarget;

  public static Gender getGenderFromId(int id) {
    return Gender.values()[id];
  }
}
