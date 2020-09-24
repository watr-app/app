/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.hydration;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {HydrationEntity.class}, version = 1, exportSchema = false)
public abstract class HydrationDatabase extends RoomDatabase {

  // Init DAO
  public abstract HydrationDao hydrationDao();

  // Define singleton instance and thread count
  private static volatile HydrationDatabase INSTANCE;
  private static final int THREAD_COUNT = 4;

  // Create multi-threaded write executor
  static final ExecutorService executor = Executors
      .newFixedThreadPool(THREAD_COUNT);

  // Singleton instance initialiser / getter
  static HydrationDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (HydrationDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HydrationDatabase.class,
              "hydration_database").build();
        }
      }
    }

    return INSTANCE;
  }
}
