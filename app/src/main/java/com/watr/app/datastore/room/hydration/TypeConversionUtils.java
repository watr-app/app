/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.room.hydration;

import androidx.room.TypeConverter;
import com.watr.app.constants.DrinkType;
import com.watr.app.utils.StringifyUtils;
import java.util.Date;
import lombok.NonNull;

/**
 * Room type conversion utilities for complex types that Room cannot natively handle.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class TypeConversionUtils {

  /**
   * Serialises a Date object from a Unix timestamp.
   *
   * @param unixTimestamp Unix timestamp
   * @return {@link Date} Serialised Date object
   */
  @TypeConverter
  public static Date dateFromUnixTimestamp(@NonNull long unixTimestamp) {
    return new Date(unixTimestamp);
  }

  /**
   * Converts a Date object to a Unix timestamp (Long)
   *
   * @param date Date object
   * @return {@link Long} Unix timestamp
   */
  @TypeConverter
  public static long dateToUnixTimestamp(@NonNull Date date) {
    return date.getTime();
  }

  /**
   * Stringifies a DrinkType enum to JSON via the Gson library.
   *
   * @param drinkType DrinkType enum
   * @return {@link String} JSON-ified enum
   */
  @TypeConverter
  public static String stringifyDrinkType(@NonNull DrinkType drinkType) {
    return StringifyUtils.gson.toJson(drinkType);
  }

  /**
   * Serialises a JSON-ified DrinkType to a DrinkType enum
   *
   * @param stringified JSON-ified enum
   * @return {@link DrinkType}
   */
  @TypeConverter
  public static DrinkType serializeDrinkType(@NonNull String stringified) {
    return StringifyUtils.gson.fromJson(stringified, DrinkType.class);
  }
}
