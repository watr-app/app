package com.watr.app.datastore.hydration;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
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
  private final double amount;

  @Getter
  @NonNull
  private final int timestamp;
}
