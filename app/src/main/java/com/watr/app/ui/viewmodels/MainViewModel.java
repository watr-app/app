/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.watr.app.datastore.room.hydration.HydrationDatabaseController;
import com.watr.app.datastore.room.hydration.HydrationEntity;
import java.util.List;
import java.util.concurrent.Future;
import lombok.Getter;
import lombok.NonNull;

/**
 * ViewModel class to create a unified access point to the database controller that survives across
 * activity lifecycles.
 *
 * <p>All methods in this class are merely wrappers for those found in {@link HydrationDatabaseController} (Which in turn are merely
 * execution schedulers for {@link com.watr.app.datastore.room.hydration.HydrationDao}). For
 * documentation on the methods themselves, see the HydrationDao interface ({@link
 * com.watr.app.datastore.room.hydration.HydrationDao}).
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class MainViewModel extends AndroidViewModel {
  private HydrationDatabaseController dbController;
  @Getter private LiveData<List<HydrationEntity>> allHydrationRecords;

  public MainViewModel(@NonNull Application application) {
    super(application);
    dbController = new HydrationDatabaseController(application);
    allHydrationRecords = dbController.getAllHydrationRecords();
  }

  public Future<HydrationEntity> getLatest() {
    return dbController.getLatest();
  }

  public Future<List<HydrationEntity>> getByTimeFrame(long start, long end) {
    return dbController.getByTimeFrame(start, end);
  }

  public void wipe() {
    dbController.wipe();
  }

  public void insert(HydrationEntity newEntity) {
    dbController.insert(newEntity);
  }

  public void bulkInsert(List<HydrationEntity> newEntityList) {
    dbController.bulkInsert(newEntityList);
  }

  public void update(HydrationEntity patcherEntity) {
    dbController.update(patcherEntity);
  }

  public void bulkUpdate(List<HydrationEntity> patcherEntityList) {
    dbController.bulkUpdate(patcherEntityList);
  }

  public void delete(HydrationEntity matcherEntity) {
    dbController.delete(matcherEntity);
  }

  public void bulkDelete(List<HydrationEntity> matcherEntityList) {
    dbController.bulkDelete(matcherEntityList);
  }
}
