/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.val;

/**
 * Miscellaneous time utilities.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class TimeUtils {
  /**
   * Converts a LocalTime object to a Unix timestamp within the current date. Accuracy depends on
   * the accuracy of the return value of <a
   * href="https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#systemDefault--">ZoneId.systemDefault()</a>,
   * which in turn depends on the accuracy of the return value of <a
   * href="https://docs.oracle.com/javase/8/docs/api/java/util/TimeZone.html#getDefault--">TimeZone.getDefault()</a>.
   *
   * @param relativeTime LocalTime object
   * @return Unix timestamp as a long integer
   */
  public static long localTimeToUnixTimestamp(LocalTime relativeTime) {
    // This is quite a bit of a faff, but turns out that "relative time within an absolute date" is
    // actually a really hard thing to pull off from a logical standpoint

    val today = LocalDate.now(); // Get the current date
    val systemTimeZone = ZoneId.systemDefault(); // Get the system time zone

    // Return the provided relative time at the determined "today" in the time zone the system is in
    // (Or, well, at least the time zone it thinks it's in, based on what the JVM or host OS is
    // telling it it's in - or, annoyingly, if it can't figure that out, it just defaults to GMT)
    // Calendars and computing do not go well together
    return relativeTime.atDate(today).atZone(systemTimeZone).toEpochSecond();
  }
}
