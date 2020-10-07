/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.utils;

import com.watr.app.constants.DateOffset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * Utilities centered around dealing with logically complex time evaluations.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class TimeUtils {
  // Developer note: Much of the code in this class is *incredibly* complex. This is because
  // programming around human-parsable time really, really sucks. In fact, time in general
  // is just a nightmare. These functions are intended to bear a lot of that headache and
  // get things to a point where you can actually make sense of how things are supposed to work.
  // Barely.

  /**
   * Globally available PrettyTime instance.
   *
   * @see PrettyTime
   */
  public static PrettyTime prettyTime = new PrettyTime();

  /**
   * Get the absolute difference between two Unix timestamps in milliseconds.
   *
   * @param start {@link Long} Timestamp to start the interval being measured
   * @param end {@link Long} Timestamp to end the interval being measured
   * @return {@link Long} The difference between the two timestamps in milliseconds.
   */
  public static long getUnixTimestampDiff(long start, long end) {
    // Using Math.abs() to make it such that the return value will be the literal amount of time it
    // took the clock to reach from "start" to "end" - this prevents "2 hours ago" from being "-2
    // hours ago"
    return Math.abs(end - start);
  }

  /**
   * Converts a LocalTime object to a Unix timestamp within the current date. Accuracy depends on
   * the accuracy of the return value of <a
   * href="https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#systemDefault--">ZoneId.systemDefault()</a>,
   * which in turn depends on the accuracy of the return value of <a
   * href="https://docs.oracle.com/javase/8/docs/api/java/util/TimeZone.html#getDefault--">TimeZone.getDefault()</a>.
   *
   * @param relativeTime {@link LocalTime} LocalTime instance to extract Unix timestamp from
   * @return {@link Long} Unix timestamp
   */
  public static long localTimeToUnixTimestamp(@NonNull LocalTime relativeTime) {
    // This is quite a bit of a faff, but turns out that "relative time within an absolute date" is
    // actually a really hard thing to pull off from a logical standpoint

    val today = LocalDate.now(); // Get the current date
    val systemTimeZone = ZoneId.systemDefault(); // Get the system time zone

    // Return the provided relative time at the determined "today" in the time zone the system is in
    // (Or, well, at least the time zone it thinks it's in, based on what the JVM or host OS is
    // telling it it's in - or, annoyingly, if it can't figure that out, it just defaults to GMT)
    // Takeaway: Human measurements of time do not jive well with their computational equivalents
    return relativeTime.atDate(today).atZone(systemTimeZone).toEpochSecond();
  }

  /**
   * Same as {@link TimeUtils}.localTimeToUnixTimestamp(), but takes a date offset to allow
   * calculation across date boundaries.
   *
   * @param relativeTime {@link LocalTime} LocalTime instance to extract Unix timestamp from
   * @param dateOffset {@link DateOffset}
   * @return {@link Long} Unix timestamp
   */
  public static long localTimeToUnixTimestamp(
      @NonNull LocalTime relativeTime, @NonNull DateOffset dateOffset) {
    val today = LocalDate.now(); // Get the current date
    val dateAtOffset = today.plusDays(dateOffset.getOffset()); // Get date at the provided offset
    val systemTimeZone = ZoneId.systemDefault(); // Get the system time zone

    return relativeTime.atDate(dateAtOffset).atZone(systemTimeZone).toEpochSecond();
  }

  /**
   * Checks if the current time is before a time today.
   *
   * @param comparisonTime {@link LocalTime} LocalTime instance to compare
   * @return {@link Boolean} Whether the current time is before the provided time today
   */
  public static boolean currentTimeIsBefore(LocalTime comparisonTime) {
    return LocalTime.now().isBefore(comparisonTime);
  }

  /**
   * Same as {@link TimeUtils}.currentTimeIsBefore(), but takes a date offset to allow calculation
   * across date boundaries.
   *
   * @param comparisonTime {@link LocalTime} LocalTime instance to compare
   * @param dateOffset {@link DateOffset}
   * @return {@link Boolean} Whether current time is before the provided LocalTime instance
   */
  public static boolean currentTimeIsBefore(
      @NonNull LocalTime comparisonTime, @NonNull DateOffset dateOffset) {
    val today = LocalDate.now();
    val comparisonTimeAtOffset = comparisonTime.atDate(today.plusDays(dateOffset.getOffset()));
    val now = LocalTime.now().atDate(today);

    return now.isBefore(comparisonTimeAtOffset);
  }

  /**
   * Checks if the current time is after a time today.
   *
   * @param comparisonTime {@link LocalTime} LocalTime instance to compare
   * @return {@link Boolean} Whether the current time is after the provided time today
   */
  public static boolean currentTimeIsAfter(LocalTime comparisonTime) {
    return LocalTime.now().isAfter(comparisonTime);
  }

  /**
   * Same as {@link TimeUtils}.currentTimeIsAfter(), but takes a date offset to allow calculation
   * across date boundaries.
   *
   * @param comparisonTime {@link LocalTime} LocalTime instance to compare
   * @param dateOffset {@link DateOffset} DateOffset enum
   * @return {@link Boolean} Whether current time is after the provided LocalTime instance yesterday
   */
  public static boolean currentTimeIsAfter(
      @NonNull LocalTime comparisonTime, @NonNull DateOffset dateOffset) {
    val today = LocalDate.now();
    val comparisonTimeAtOffset = comparisonTime.atDate(today.plusDays(dateOffset.getOffset()));
    val now = LocalTime.now().atDate(today);

    return now.isAfter(comparisonTimeAtOffset);
  }

  /**
   * Checks if current time is between two LocalTime instances (As in is in their interval).
   *
   * @param start {@link LocalTime} Interval-starting LocalTime instance
   * @param end {@link LocalTime} Interval-ending LocalTime instance
   * @return {@link Boolean} Whether current time is between the provided LocalTime instances
   */
  public static boolean currentTimeIsInInterval(LocalTime start, LocalTime end) {
    val now = LocalTime.now();
    return now.isAfter(start) && now.isBefore(end);
  }

  /**
   * Same as {@link TimeUtils}.currentTimeIsInInterval(), but takes start and end date offsets to
   * allow calculation across date boundaries.
   *
   * @param start {@link LocalTime} Interval-starting LocalTime instance
   * @param startOffset {@link DateOffset} DateOffset enum for the start of the interval
   * @param end {@link LocalTime} Interval-ending LocalTime instance
   * @param endOffset {@link DateOffset} DateOffset enum for the end of the interval
   * @return {@link Boolean} Whether current time is between the provided LocalTime instances
   */
  public static boolean currentTimeIsInterval(
      @NonNull LocalTime start,
      @NonNull DateOffset startOffset,
      @NonNull LocalTime end,
      @NonNull DateOffset endOffset) {
    val now = LocalTime.now();
    val today = LocalDate.now();

    val startTimeAtOffset = start.atDate(today.plusDays(startOffset.getOffset()));
    val endTimeAtOffset = end.atDate(today.plusDays(endOffset.getOffset()));

    return now.atDate(today).isAfter(startTimeAtOffset)
        && now.atDate(today).isBefore(endTimeAtOffset);
  }

  /**
   * Parses hour and minute integers to LocalTime.
   *
   * @param hours {@link Integer}
   * @param minutes {@link Integer}
   * @return {@link LocalTime} Parsed LocalTime instance
   */
  public static LocalTime parseHoursAndMinutesToLocalTime(int hours, int minutes) {
    val hourString = StringUtils.leftPad(Integer.toString(hours), 2, "0");
    val minuteString = StringUtils.rightPad(Integer.toString(minutes), 2, "0");
    return LocalTime.parse(String.format("%s:%s:00", hourString, minuteString));
  }
}
