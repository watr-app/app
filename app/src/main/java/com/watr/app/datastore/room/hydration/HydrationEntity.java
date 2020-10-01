/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
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

  @Getter private final double amount; // I.e. absolute amount of liquid ingested, in ml

  // BUG: Cannot use a RequiredArgsConstructor here or it breaks the build because it cannot find
  // the setter for some reason, hence why the constructor has to be manually created here
  public HydrationEntity(DrinkType drinkType, double amount, Date timestamp) {
    this.drinkType = drinkType;
    this.amount = amount;
    this.timestamp = timestamp;
  }
}
