package com.watr.app.datastore.hydration;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.ArrayList;

@Dao
public interface HydrationDao {

  // Using ignore conflict resolution strategy because two records will never be the same,
  // seeing as they will at the very least have different IDs and Unix timestamps
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(HydrationEntity newEntity);

  // Update a hydration record through a patcher entity (Entity with matching primary key is deleted)
  // https://developer.android.com/reference/androidx/room/Update
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void update(HydrationEntity patcherEntity);

  // Bulk update hydration records with a matcher list
  // https://developer.android.com/reference/androidx/room/Update
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void bulkUpdate(ArrayList<HydrationEntity> patcherEntityList);

  // Delete a hydration record through a matcher (Entity with matching primary key is deleted)
  // https://developer.android.com/reference/androidx/room/Delete
  @Delete
  void delete(HydrationEntity matcherEntity);

  // Bulk delete hydration records with a matcher list
  @Delete
  void bulkDelete(ArrayList<HydrationEntity> matcherEntityList);

  // Wipe the entire database (Use with caution)
  @Query("DELETE FROM hydration_records")
  void wipe();

  // Get all hydration records ordered by latest to oldest (Largest => smallest Unix timestamp)
  @Query("SELECT * FROM hydration_records ORDER BY timestamp DESC")
  ArrayList<HydrationEntity> getAll();

  // Get all hydration records in a certain time frame, sorted by latest to oldest
  @Query("SELECT * FROM hydration_records WHERE timestamp >= :start AND timestamp <= :end ORDER BY timestamp DESC")
  ArrayList<HydrationEntity> getByTimeFrame(int start, int end);
}