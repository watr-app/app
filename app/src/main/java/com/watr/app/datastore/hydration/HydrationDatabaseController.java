/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import lombok.Getter;

public class HydrationDatabaseController {

  private HydrationDao hydrationDao;

  @Getter
  private LiveData<ArrayList<HydrationEntity>> allHydrationRecords;

  // FIXME: If we want to unit test this, we can't depend on Application
  // Should be fine for now though
  private HydrationDatabaseController(Application application) {
    HydrationDatabase db = HydrationDatabase.getDatabase(application);
    hydrationDao = db.hydrationDao();
    allHydrationRecords = hydrationDao.getAll();
  }

  // Note: All methods must be called on non-UI threads, or the app will throw an exception
  // This is to prevent running queries on the main thread and thus threadblocking the UI

  // All methods here are merely execution scheduler shell methods for the DAO
  // For documentation on the methods themselves, see the HydrationDao interface (HydrationDao.java)

  void wipe() {
    HydrationDatabase.executor.execute(() -> hydrationDao.wipe());
  }

  void insert(HydrationEntity newEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.insert(newEntity));
  }

  void update(HydrationEntity patcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.update(patcherEntity));
  }

  void bulkUpdate(ArrayList<HydrationEntity> patcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkUpdate(patcherEntityList));
  }

  void delete(HydrationEntity matcherEntity) {
    HydrationDatabase.executor.execute(() -> hydrationDao.delete(matcherEntity));
  }

  void bulkDelete(ArrayList<HydrationEntity> matcherEntityList) {
    HydrationDatabase.executor.execute(() -> hydrationDao.bulkDelete(matcherEntityList));
  }
}
