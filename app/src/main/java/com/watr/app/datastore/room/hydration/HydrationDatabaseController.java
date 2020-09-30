/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.room.hydration;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import lombok.Getter;

/**
 * Hydration database controller / repository.
 *
 * <p>All methods here are merely execution scheduler shell methods for the DAO. For documentation
 * on the methods themselves, see the HydrationDao interface (HydrationDao.java).
 *
 * <p>Note: All methods must be called on non-UI threads, or the app will throw an exception. This
 * is to prevent running queries on the main thread and thus threadblocking the UI.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class HydrationDatabaseController {

  private HydrationDao hydrationDao;

  @Getter private LiveData<List<HydrationEntity>> allHydrationRecords;

  // FIXME: If we want to unit test this, we can't depend on Application
  // Should be fine for now though
  public HydrationDatabaseController(Application application) {
    HydrationDatabase db = HydrationDatabase.getDatabase(application);
    hydrationDao = db.hydrationDao();
    allHydrationRecords = hydrationDao.getAll();
  }

  public Future<HydrationEntity> getLatest() {
    return HydrationDatabase.executor.submit(() -> hydrationDao.getLatest());
  }

  public Future<List<HydrationEntity>> getByTimeFrame(Date start, Date end) {
    return HydrationDatabase.executor.submit(
        () -> hydrationDao.getByTimeFrame(start.getTime(), end.getTime()));
  }

  void wipe() {
    HydrationDatabase.executor.execute(() -> hydrationDao.wipe());
  }

  void insert(HydrationEntity newEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.insert(newEntity));
  }

  void bulkInsert(List<HydrationEntity> newEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkInsert(newEntityList));
  }

  void update(HydrationEntity patcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.update(patcherEntity));
  }

  void bulkUpdate(List<HydrationEntity> patcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkUpdate(patcherEntityList));
  }

  void delete(HydrationEntity matcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.delete(matcherEntity));
  }

  void bulkDelete(List<HydrationEntity> matcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkDelete(matcherEntityList));
  }
}
