/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.datastore.sharedpreferences.userprofile;

import android.content.SharedPreferences;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceManager;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceNotFoundException;
import com.watr.app.datastore.sharedpreferences.SharedPreferenceType;
import com.watr.app.hydration.Gender;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NonNull;

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
   * @throws SharedPreferenceNotFoundException if setting has not been initialised during setup
   */
  public Gender getGender() throws SharedPreferenceNotFoundException {
    if (!ctx.contains("gender")) {
      throw new SharedPreferenceNotFoundException(
          "Critical user profile setting 'gender' not found");
    }

    // Using 0 as the default even though this value will always be set
    return Gender.getGenderFromId(ctx.getInt("gender", 0));
  }

  /**
   * Gets the user's daily drink target.
   *
   * @return Daily drink target in millilitres
   * @throws SharedPreferenceNotFoundException if setting has not been initialised during setup
   */
  public int getDailyTarget() throws SharedPreferenceNotFoundException {
    if (!ctx.contains("dailyTarget")) {
      throw new SharedPreferenceNotFoundException(
          "Critical user profile setting 'dailyTarget' not found");
    }

    // Getting the default from Gender even though this value will always be set
    return ctx.getInt("dailyTarget", this.getGender().getDefaultDailyTarget());
  }

  /**
   * Gets the user's preferred wake up time.
   *
   * @return LocalTime object representing the user's preferred wake up time
   * @throws SharedPreferenceNotFoundException if setting has not been initialised during setup
   */
  public LocalTime getWakeTime() throws SharedPreferenceNotFoundException {
    if (!ctx.contains("wakeTime")) {
      throw new SharedPreferenceNotFoundException(
          "Critical user profile setting 'wakeTime' not found");
    }

    // Using noon as default even though this value will always be set
    return LocalTime.parse(ctx.getString("wakeTime", LocalTime.NOON.toString()));
  }

  /**
   * Gets the user's preferred bed time.
   *
   * @return LocalTime object representing the user's preferred bed time
   * @throws SharedPreferenceNotFoundException if setting has not been initialised during setup
   */
  public LocalTime getBedTime() throws SharedPreferenceNotFoundException {
    if (!ctx.contains("bedTime")) {
      throw new SharedPreferenceNotFoundException(
          "Critical user profile setting 'bedTime' not found");
    }

    // Using midnight (Min) as default even though this value will always be set
    return LocalTime.parse(ctx.getString("bedTime", LocalTime.MIN.toString()));
  }
}
