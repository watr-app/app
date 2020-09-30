/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences.userprofile;

import android.content.SharedPreferences;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceManager;
import com.watr.app.datastore.sharedpreferences.DataStorePreFlightCheckException;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceType;
import com.watr.app.hydration.Gender;
import java.time.LocalTime;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

/**
 * User profile information manager leveraging SharedPreferences.
 *
 * @author mhgitti
 * @version 1.0.0
 */
public class UserProfileManager extends SharedPreferenceManager {

  @Getter private final SharedPreferences ctx;

  public UserProfileManager(SharedPreferences ctx) {
    super(ctx);
    this.ctx = ctx;
  }

  public void checkRequiredProfileSettings() throws DataStorePreFlightCheckException {
    val requiredKeys = new ArrayList<String>();
    requiredKeys.add("gender");
    requiredKeys.add("dailyTarget");
    requiredKeys.add("wakeTime");
    requiredKeys.add("bedTime");

    val notDefined = requiredKeys.stream().filter(key -> !ctx.contains(key)).toArray();

    if (notDefined.length > 0) {
      throw new DataStorePreFlightCheckException(
          String.format("Required user profile settings %s not defined", notDefined.toString()));
    }
  }

  /**
   * Sets the user's gender.
   *
   * @param gender Gender enum
   */
  public void setGender(@NonNull Gender gender) {
    super.editAndApply(SharedPreferenceType.INTEGER, "genderId", gender.getGenderId());
  }

  /**
   * Sets the daily drink target for the user.
   *
   * @param millilitres Drink target in millilitres
   */
  public void setDailyTarget(int millilitres) {
    super.editAndApply(SharedPreferenceType.INTEGER, "dailyTarget", millilitres);
  }

  /**
   * Sets the wake up time for the user.
   *
   * @param time User's preferred wake-up time
   */
  public void setWakeTime(@NonNull LocalTime time) {
    super.editAndApply(SharedPreferenceType.STRING, "wakeTime", time.toString());
  }

  /**
   * Sets the bed time for the user.
   *
   * @param time User's preferred bed time
   */
  public void setBedTime(@NonNull LocalTime time) {
    super.editAndApply(SharedPreferenceType.STRING, "bedTime", time.toString());
  }

  /**
   * Gets the user's gender.
   *
   * @return Gender enum
   */
  public Gender getGender() {
    // Using 0 as the default even though this value will always be set
    return Gender.getGenderFromId(ctx.getInt("gender", 0));
  }

  /**
   * Gets the user's daily drink target.
   *
   * @return Daily drink target in millilitres
   */
  public int getDailyTarget() {
    // Getting the default from Gender even though this value will always be set
    return ctx.getInt("dailyTarget", this.getGender().getDefaultDailyTarget());
  }

  /**
   * Gets the user's preferred wake up time.
   *
   * @return LocalTime object representing the user's preferred wake up time
   */
  public LocalTime getWakeTime() {
    // Using noon as default even though this value will always be set
    return LocalTime.parse(ctx.getString("wakeTime", LocalTime.NOON.toString()));
  }

  /**
   * Gets the user's preferred bed time.
   *
   * @return LocalTime object representing the user's preferred bed time
   */
  public LocalTime getBedTime() {
    // Using midnight (Min) as default even though this value will always be set
    return LocalTime.parse(ctx.getString("bedTime", LocalTime.MIN.toString()));
  }
}
