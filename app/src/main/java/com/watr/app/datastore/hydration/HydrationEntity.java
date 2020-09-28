/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.watr.app.hydration.DrinkType;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Entity(tableName = "hydration_records")
public class HydrationEntity {
  @Getter
  @Setter
  @PrimaryKey(autoGenerate = true)
  private int id;

  @Getter
  @Setter
  private DrinkType drinkType;

  @Getter
  @Setter
  private double amount; // I.e. absolute amount of liquid ingested, in ml

  @Getter
  @Setter
  private Date timestamp;

}
