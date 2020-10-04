/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.datastore.room.hydration;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * Hydration DAO (Data Access Object).
 *
 * @see Dao
 * @author linuswillner
 * @version 1.0.0
 */
@Dao
public interface HydrationDao {

  /**
   * Gets all hydration records.
   *
   * @return {@link LiveData}<{@link List}<{@link HydrationEntity}>> All hydration records ordered
   *     by latest to oldest (Largest => smallest Unix timestamp)
   */
  @Query("SELECT * FROM hydration_records ORDER BY timestamp DESC")
  LiveData<List<HydrationEntity>> getAll();

  /**
   * Gets hydration records between two Unix timestamps. Start and end are inclusive.
   *
   * @param start {@link Long} Record range start timestamp
   * @param end {@link Long} Record range end timestamp
   * @return {@link List}<{@link HydrationEntity}> All hydration records between start and end
   */
  @Query(
      "SELECT * FROM hydration_records WHERE timestamp <= :start AND timestamp >= :end ORDER BY timestamp DESC LIMIT 1")
  List<HydrationEntity> getByTimeFrame(long start, long end);

  /** Wipes the entire database. Use with caution. */
  @Query("DELETE FROM hydration_records")
  void wipe();

  // Note: Using ignore conflict resolution strategy here, because two records will never be the
  // same, seeing as they will have different Unix timestamps

  /**
   * Adds a new hydration record to the database.
   *
   * @param newEntity {@link HydrationEntity} New hydration record (Entity)
   */
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(HydrationEntity newEntity);

  /**
   * Adds new hydration records to the database in bulk.
   *
   * @param newEntityList {@link List}<{@link HydrationEntity}> List of new hydration records
   *     (Entities)
   */
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void bulkInsert(List<HydrationEntity> newEntityList);

  /**
   * Updates a hydration record through a patcher entity. An entity with a matching primary key will
   * be updated.
   *
   * @see Update
   * @param patcherEntity {@link HydrationEntity} Hydration record to update
   */
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void update(HydrationEntity patcherEntity);

  /**
   * Updates hydration records in bulk with a list of patcher entities. Entities with matching
   * primary keys will be updated.
   *
   * @see Update
   * @param patcherEntityList {@link List}<{@link HydrationEntity}> Hydration records to update
   */
  @Update(onConflict = OnConflictStrategy.IGNORE)
  void bulkUpdate(List<HydrationEntity> patcherEntityList);

  /**
   * Delete a hydration record through a matcher, i.e. an entity with a matching primary key will be
   * deleted.
   *
   * @see Delete
   * @param matcherEntity {@link HydrationEntity} Hydration record to delete
   */
  @Delete
  void delete(HydrationEntity matcherEntity);

  /**
   * Deletes hydration records in bulk with a matcher, i.e. an entity with a matching primary key
   * will be deleted.
   *
   * @see Delete
   * @param matcherEntityList {@link List}<{@link HydrationEntity}> Hydration records to delete
   */
  @Delete
  void bulkDelete(List<HydrationEntity> matcherEntityList);
}
