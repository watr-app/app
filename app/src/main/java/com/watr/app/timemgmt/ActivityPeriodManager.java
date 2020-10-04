/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.timemgmt;

import com.watr.app.constants.ActivityPeriod;
import com.watr.app.constants.DateOffset;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.activities.MainActivity;
import com.watr.app.utils.TimeUtils;
import lombok.val;

/**
 * Activity period management and calculation utilities.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class ActivityPeriodManager {
  private static UserProfileManager userProfileManager = MainActivity.getUserProfileManager();

  /**
   * Returns the current activity period. Wrapper for {@link
   * ActivityPeriodManager}.getCurrentActivityPeriodWithOffsets().
   *
   * @return {@link ActivityPeriod}
   * @throws UnknownTimeIntervalException If time interval wasn't wake => bed or bed => wake (Which
   *     should be downright physically impossible).
   */
  public static ActivityPeriod getCurrentActivityPeriod() throws UnknownTimeIntervalException {
    return getCurrentActivityPeriodWithOffsets().activityPeriod;
  }

  /**
   * Returns the current activity period, compensating for times potentially hitting across date
   * boundaries. Additionally returns the calculated offsets for the wake time and bed time.
   *
   * @return {@link ActivityPeriodWithOffsets}
   * @throws UnknownTimeIntervalException if time interval was not wake => bed or bed => wake (Which
   *     should be downright physically impossible).
   */
  public static ActivityPeriodWithOffsets getCurrentActivityPeriodWithOffsets()
      throws UnknownTimeIntervalException {
    val wakeTime = userProfileManager.getWakeTime();
    val bedTime = userProfileManager.getBedTime();

    DateOffset wakeTimeOffset = DateOffset.TODAY;
    DateOffset bedTimeOffset = DateOffset.TODAY;

    // Figure out what the current activity period is
    // Going to from here on in abbreviate wake time as W and bed time as B to save space

    // Is between wake time and bed time today
    val isBetweenWB = TimeUtils.currentTimeIsInInterval(wakeTime, bedTime);
    // Is between wake time today and bed time tomorrow
    val isBetweenWTodayAndBTomorrow =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.TODAY, bedTime, DateOffset.TOMORROW);
    // Is between wake time yesterday and bed time today
    val isBetweenWYesterdayAndBToday =
        TimeUtils.currentTimeIsInterval(wakeTime, DateOffset.YESTERDAY, bedTime, DateOffset.TODAY);

    // Is between bed time and wake time today
    val isBetweenBW = TimeUtils.currentTimeIsInInterval(bedTime, wakeTime);
    // Is between bed time yesterday and wake time today
    val isBetweenBYesterdayAndWToday =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.YESTERDAY, wakeTime, DateOffset.TODAY);
    // Is between bed time today and wake time tomorrow
    val isBetweenBTodayAndWTomorrow =
        TimeUtils.currentTimeIsInterval(bedTime, DateOffset.TODAY, wakeTime, DateOffset.TOMORROW);

    // Make the conclusions

    // Wake time offset
    // Not checking for W => B as that's the default condition
    if (isBetweenWTodayAndBTomorrow) {
      wakeTimeOffset = DateOffset.TODAY;
      bedTimeOffset = DateOffset.TOMORROW;
    } else if (isBetweenWYesterdayAndBToday) {
      wakeTimeOffset = DateOffset.YESTERDAY;
      bedTimeOffset = DateOffset.TODAY;
    }

    // Bed time offset
    // Not checking for B => W as that's the default condition
    if (isBetweenBYesterdayAndWToday) {
      wakeTimeOffset = DateOffset.TODAY;
      bedTimeOffset = DateOffset.YESTERDAY;
    } else if (isBetweenBTodayAndWTomorrow) {
      wakeTimeOffset = DateOffset.TOMORROW;
      bedTimeOffset = DateOffset.TODAY;
    }

    val isAwake = isBetweenWB || (isBetweenWTodayAndBTomorrow || isBetweenWYesterdayAndBToday);
    val isAsleep = isBetweenBW || (isBetweenBYesterdayAndWToday || isBetweenBTodayAndWTomorrow);

    // Return the result

    if (isAwake) {
      return new ActivityPeriodWithOffsets(ActivityPeriod.AWAKE, wakeTimeOffset, bedTimeOffset);
    } else if (isAsleep) {
      return new ActivityPeriodWithOffsets(ActivityPeriod.ASLEEP, wakeTimeOffset, bedTimeOffset);
    } else {
      // Throwing here because the only possible states to be in are the ones defined above; time
      // can physically speaking only be during one of those periods. In other words, if this
      // throws, something is really, really, *really* wrong.
      throw new UnknownTimeIntervalException("Time interval wasn't wake => bed or bed => wake");
    }
  }
}
