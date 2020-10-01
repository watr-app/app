/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.timemgmt;

import com.watr.app.MainActivity;
import com.watr.app.constants.ActivityPeriod;
import com.watr.app.constants.DateOffset;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import java.time.LocalTime;
import lombok.val;

public class ActivityPeriodManager {
  private static UserProfileManager userProfileManager = MainActivity.getUserProfileManager();

  public static ActivityPeriod getCurrentActivityPeriod() throws UnknownTimeIntervalException {
    return checkActivityPeriod();
  }

  private static ActivityPeriod checkActivityPeriod() throws UnknownTimeIntervalException {
    val wakeTime = userProfileManager.getWakeTime();
    val bedTime = userProfileManager.getBedTime();

    // Going to from here on in abbreviate wake time as W and bed time as B to save space

    // Is between wake time and bed time today
    val isBetweenWB = TimeUtils.currentTimeIsInInterval(wakeTime, bedTime);
    // Is between wake time today and bed time tomorrow
    val isBetweenWTodayAndBTomorrow =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.TODAY, bedTime, DateOffset.TOMORROW);
    // Is between wake time yesterday and bed time today
    val isBetweenWYesterdayAndBToday =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.YESTERDAY, bedTime, DateOffset.TODAY);

    val isAwake = isBetweenWB || (isBetweenWTodayAndBTomorrow || isBetweenWYesterdayAndBToday);

    // Is between bed time and wake time today
    val isBetweenBW = TimeUtils.currentTimeIsInInterval(bedTime, wakeTime);
    // Is between bed time yesterday and wake time today
    val isBetweenBYesterdayAndWToday =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.YESTERDAY, wakeTime, DateOffset.TODAY);
    // Is between bed time today and wake time tomorrow
    val isBetweenBTodayAndWTomorrow =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.TODAY, wakeTime, DateOffset.TOMORROW);

    val isAsleep = isBetweenBW || (isBetweenBYesterdayAndWToday || isBetweenBTodayAndWTomorrow);

    if (isAwake) {
      return ActivityPeriod.AWAKE;
    } else if (isAsleep) {
      return ActivityPeriod.ASLEEP;
    } else {
      // Throwing here because the only possible states to be in are the ones defined above; time
      // can physically speaking only be during one of those periods. In other words, if this
      // throws, something is really, really, *really* wrong.
      throw new UnknownTimeIntervalException("Time interval wasn't wake => bed or bed => wake");
    }
  }

  public static ActivityPeriodOffset getActivityPeriodOffsets(
      LocalTime wakeTime, LocalTime bedTime) {
    DateOffset wakeTimeOffset = DateOffset.TODAY;
    DateOffset bedTimeOffset = DateOffset.TODAY;

    // Abbreviating wake time as W and bed time as B to save space

    // Wake time offset
    // Not checking for W => B as that's the default condition
    val isBetweenWTodayAndBTomorrow =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.TODAY, bedTime, DateOffset.TOMORROW);
    val isBetweenWYesterdayAndBToday =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.YESTERDAY, bedTime, DateOffset.TODAY);

    if (isBetweenWTodayAndBTomorrow) {
      wakeTimeOffset = DateOffset.TODAY;
      bedTimeOffset = DateOffset.TOMORROW;
    } else if (isBetweenWYesterdayAndBToday) {
      wakeTimeOffset = DateOffset.YESTERDAY;
      bedTimeOffset = DateOffset.TODAY;
    }

    // Bed time offset
    // Not checking for B => W as that's the default condition
    val isBetweenBYesterdayAndWToday =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.YESTERDAY, wakeTime, DateOffset.TODAY);
    val isBetweenBTodayAndWTomorrow =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.TODAY, wakeTime, DateOffset.TOMORROW);

    if (isBetweenBYesterdayAndWToday) {
      wakeTimeOffset = DateOffset.TODAY;
      bedTimeOffset = DateOffset.YESTERDAY;
    } else if (isBetweenBTodayAndWTomorrow) {
      wakeTimeOffset = DateOffset.TOMORROW;
      bedTimeOffset = DateOffset.TODAY;
    }

    return new ActivityPeriodOffset(wakeTimeOffset, bedTimeOffset);
  }
}
