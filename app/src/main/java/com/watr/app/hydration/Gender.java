/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.hydration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
  MALE(0),
  FEMALE(1);

  public final int genderId;
}
