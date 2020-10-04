/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.datastore.room.hydration;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.watr.app.constants.DrinkType;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;

/**
 * Hydration record.
 *
 * @author linuswillner
 * @version 1.0.0
 */
@ToString
@Entity(tableName = "hydration_records")
public class HydrationEntity {
  @Getter @PrimaryKey private final Date timestamp;

  @Getter
  @ColumnInfo(name = "drink_type")
  private final DrinkType drinkType;

  // Absolute amount of liquid ingested, in ml
  @Getter private final int absoluteHydration;

  // Relative amount of liquid ingested in ml, i.e. the absolute amount with drink type hydration
  // coefficient applied
  @Getter private final int relativeHydration;

  // BUG: Cannot use a RequiredArgsConstructor here or Room breaks because it thinks it does not
  // have a valid constructor, so we have to create it manually
  // (That's quite dumb, but hey, problem solved)

  // BUG: The relativeHydration parameter is intentionally ignored here because Room will chuck a
  // hissy fit if all getter parameters do not have setters - which, as it turns out, means fields
  // with exact same variable names in the constructor parameters
  // (...that makes no God damn sense, but fine, we'll roll with it)
  public HydrationEntity(
      Date timestamp, DrinkType drinkType, int absoluteHydration, int relativeHydration) {
    this.drinkType = drinkType;
    this.absoluteHydration = absoluteHydration;
    // We can afford to do an integer coercion here because, in reality, all the variables with this
    // are going to end up
    this.relativeHydration = (int) (absoluteHydration * drinkType.getHydrationCoefficient());
    this.timestamp = timestamp;
  }
}
