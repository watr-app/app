/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.watr.app.hydration.DrinkType;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;

/**
 * Hydration record.
 */
@ToString
@Entity(tableName = "hydration_records")
public class HydrationEntity {
  // BUG: Cannot use a RequiredArgsConstructor here or it breaks the build because it cannot find
  // the setter for some reason, hence why the constructor has to be manually created here
  public HydrationEntity(int id, DrinkType drinkType, double amount, Date timestamp) {
    this.id = id;
    this.drinkType = drinkType;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  @Getter
  @PrimaryKey(autoGenerate = true)
  private final int id;

  @Getter
  @ColumnInfo(name = "drink_type")
  private final DrinkType drinkType;

  @Getter private final double amount; // I.e. absolute amount of liquid ingested, in ml

  @Getter private final Date timestamp;
}
