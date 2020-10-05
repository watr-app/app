/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Constants for date offsets.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@RequiredArgsConstructor
public enum DateOffset {
  YESTERDAY(-1),
  TODAY(0),
  TOMORROW(1);

  @Getter private final int offset;
}
