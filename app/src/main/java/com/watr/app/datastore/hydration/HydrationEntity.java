/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.watr.app.hydration.DrinkType;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity(tableName = "hydration_records")
public class HydrationEntity {

  @Getter
  @PrimaryKey(autoGenerate = true)
  private int id;

  @Getter
  @NonNull
  private final DrinkType drinkType;

  @Getter
  @NonNull
  private final double amount; // I.e. absolute amount of liquid ingested

  @Getter
  @NonNull
  private final int timestamp;
}
