/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DateOffset {
  YESTERDAY(-1),
  TODAY(0),
  TOMORROW(1);

  @Getter private final int offset;
}
