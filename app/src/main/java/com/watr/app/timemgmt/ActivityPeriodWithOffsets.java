/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.timemgmt;


import com.watr.app.constants.ActivityPeriod;
import com.watr.app.constants.DateOffset;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityPeriodWithOffsets {
  public final ActivityPeriod activityPeriod;
  public final DateOffset wakeTimeOffset;
  public final DateOffset bedTimeOffset;
}
