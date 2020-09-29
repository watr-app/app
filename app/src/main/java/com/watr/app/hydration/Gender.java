/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.hydration;

import lombok.AllArgsConstructor;

/**
 * Record of biological genders and their IDs.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@AllArgsConstructor
public enum Gender {
  MALE(0),
  FEMALE(1);

  private int gender;

  public Gender(int gender) {
    this.gender = gender;
  }

  public int getGender() {
    return this.gender;
  }
}
