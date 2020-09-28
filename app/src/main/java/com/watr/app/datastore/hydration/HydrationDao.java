/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface HydrationDao {

  // Get all hydration records ordered by latest to oldest (Largest => smallest Unix timestamp)
  @Query("SELECT * FROM hydration_records ORDER BY timestamp DESC")
  LiveData<List<HydrationEntity>> getAll();

  // Get latest hydration record
  @Query("SELECT * FROM hydration_records ORDER BY timestamp DESC LIMIT 1")
  HydrationEntity getLatest();

  // Get hydration records between two Unix timestamps
  @Query("SELECT * FROM hydration_records WHERE timestamp <= :start AND timestamp >= :end ORDER BY timestamp DESC LIMIT 1")
  List<HydrationEntity> getByTimeFrame (Long start, Long end);

  // Wipe the entire database (Use with caution)
  @Query("DELETE FROM hydration_records")
  void wipe();

  // Using ignore conflict resolution strategy here because two records will never be the same,
  // seeing as they will at the very least have different IDs and Unix timestamps

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(HydrationEntity newEntity);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void bulkInsert(List<HydrationEntity> newEntityList);

  // Update a hydration record through a patcher entity
  // Entity with matching primary key is deleted
  // https://developer.android.com/reference/androidx/room/Update
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void update(HydrationEntity patcherEntity);

  // Bulk update hydration records with a matcher list
  // https://developer.android.com/reference/androidx/room/Update
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void bulkUpdate(List<HydrationEntity> patcherEntityList);

  // Delete a hydration record through a matcher (Entity with matching primary key is deleted)
  // https://developer.android.com/reference/androidx/room/Delete
  @Delete
  void delete(HydrationEntity matcherEntity);

  // Bulk delete hydration records with a matcher list
  @Delete
  void bulkDelete(List<HydrationEntity> matcherEntityList);
}
