/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import androidx.room.TypeConverter;
import com.watr.app.hydration.DrinkType;
import com.watr.app.utils.StringifyUtils;
import java.util.Date;
import lombok.NonNull;

public class TypeConversionUtils {

  @TypeConverter
  public static Date dateFromUnixTimestamp(@NonNull Long unixTimestamp) {
    return new Date(unixTimestamp);
  }

  @TypeConverter
  public static Long dateToUnixTimestamp(@NonNull Date date) {
    return date.getTime();
  }

  @TypeConverter
  public static String stringifyDrinkType(@NonNull DrinkType drinkType) {
    return StringifyUtils.gson.toJson(drinkType);
  }

  @TypeConverter
  public static DrinkType serializeDrinkType(@NonNull String stringified) {
    return StringifyUtils.gson.fromJson(stringified, DrinkType.class);
  }
}
