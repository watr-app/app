/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.room.hydration;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.watr.app.MainActivity;
import com.watr.app.utils.TimeUtils;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import lombok.Getter;
import lombok.val;

/**
 * Hydration database controller / repository.
 *
 * <p>All methods here are merely execution scheduler shell methods for the DAO. For documentation
 * on the methods themselves, see the HydrationDao interface ({@link
 * com.watr.app.datastore.room.hydration.HydrationDao}).
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
  @Getter private LiveData<List<HydrationEntity>> hydrationRecordsForToday;

  // FIXME: If we want to unit test this properly, we can't depend on Application
  // Should be fine for now though
  public HydrationDatabaseController(Application application) {
    HydrationDatabase db = HydrationDatabase.getDatabase(application);
    hydrationDao = db.hydrationDao();
    allHydrationRecords = hydrationDao.getAll();

    val wakeTime = MainActivity.getUserProfileManager().getWakeTime();
    val bedTime = MainActivity.getUserProfileManager().getBedTime();

    val wakeTimeEpoch = TimeUtils.localTimeToUnixTimestamp(wakeTime);
    val bedTimeEpoch = TimeUtils.localTimeToUnixTimestamp(bedTime);

    hydrationRecordsForToday = hydrationDao.getByTimeFrame(wakeTimeEpoch, bedTimeEpoch);
  }

  public Future<HydrationEntity> getLatest() {
    return HydrationDatabase.executor.submit(() -> hydrationDao.getLatest());
  }

  public void wipe() {
    HydrationDatabase.executor.execute(() -> hydrationDao.wipe());
  }

  public void insert(HydrationEntity newEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.insert(newEntity));
  }

  public void bulkInsert(List<HydrationEntity> newEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkInsert(newEntityList));
  }

  public void update(HydrationEntity patcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.update(patcherEntity));
  }

  public void bulkUpdate(List<HydrationEntity> patcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkUpdate(patcherEntityList));
  }

  public void delete(HydrationEntity matcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.delete(matcherEntity));
  }

  public void bulkDelete(List<HydrationEntity> matcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkDelete(matcherEntityList));
  }
}
