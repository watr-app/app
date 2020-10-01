/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.timemgmt;


import com.watr.app.constants.DateOffset;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityPeriodOffset {
  public final DateOffset wakeTimeOffset;
  public final DateOffset bedTimeOffset;
}
