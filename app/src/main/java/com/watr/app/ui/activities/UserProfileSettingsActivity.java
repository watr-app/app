/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import com.watr.app.constants.Gender;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.utils.InputUtils;
import com.watr.app.utils.TimeUtils;
import java.util.Objects;
import lombok.val;

/**
 * Editor for user profile settings after first setup.
 *
 * @author panueronen
 * @version 1.0.0
 */
public class UserProfileSettingsActivity extends AppCompatActivity {
  private UserProfileManager userProfileManager;
  private RadioGroup genderSelectorRadioGroup;
  private EditText dailyTargetInput;
  private TextView hourDisplay;
  private TextView minuteDisplay;
  private TextView clock12HrModifier;
  private TextView currentWakeTimeDisplay;
  private TextView currentBedTimeDisplay;
  private int hours = 0;
  private int minutes = 0;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_settings);
    Objects.requireNonNull(getSupportActionBar()).hide();

    userProfileManager = MainActivity.getUserProfileManager();

    // Fetch update targets
    genderSelectorRadioGroup = findViewById(R.id.genderSelector);
    dailyTargetInput = findViewById(R.id.dailyTargetInput);
    hourDisplay = findViewById(R.id.hourDisplay);
    minuteDisplay = findViewById(R.id.minuteDisplay);
    clock12HrModifier = findViewById(R.id.timeModifier12Hr);
    currentWakeTimeDisplay = findViewById(R.id.currentWakeTimeDisplay);
    currentBedTimeDisplay = findViewById(R.id.currentBedTimeDisplay);

    // Picks users gender so it is chosen by default
    if (userProfileManager.getGender() == Gender.MALE) {
      genderSelectorRadioGroup.check(R.id.buttonMale);
    } else {
      genderSelectorRadioGroup.check(R.id.buttonFemale);
    }

    // Append current daily target to the input
    dailyTargetInput.setText(Integer.toString(userProfileManager.getDailyTarget()));

    // Append current wake and bed times

    val wakeTime = userProfileManager.getWakeTime();
    val bedTime = userProfileManager.getBedTime();

    currentWakeTimeDisplay.setText(String.format("Wake time: %s", wakeTime.toString()));
    currentBedTimeDisplay.setText(String.format("Bed time: %s", bedTime.toString()));

    // Listener that swaps the gender
    genderSelectorRadioGroup.setOnCheckedChangeListener(
        (group, checkedId) -> {
          Gender gender;

          switch (checkedId) {
            case R.id.buttonMale:
              gender = Gender.MALE;
              break;
            case R.id.buttonFemale:
              gender = Gender.FEMALE;
              break;
            default:
              throw new IllegalStateException(
                  String.format(
                      "Received unexpected gender choice: Expected %d for male or %d for female, but got %d",
                      R.id.buttonMale, R.id.buttonFemale, checkedId));
          }

          userProfileManager.setGender(gender);
          userProfileManager.setDailyTarget(gender.getDefaultDailyTarget());
          dailyTargetInput.setText(Integer.toString(userProfileManager.getDailyTarget()));
        });
  }

  public void confirmDailyTarget(View view) {
    if (InputUtils.inputIsEmptyOrZero(dailyTargetInput)) {
      dailyTargetInput.setError("Daily target must be non-zero!");
    } else {
      dailyTargetInput.setError(null);
      userProfileManager.setDailyTarget(InputUtils.getInteger(dailyTargetInput));
    }
  }

  public void increaseHour(View view) {
    if (hours + 1 > 23) {
      hours = 0;
    } else {
      hours++;
    }

    updateCounters();
  }

  public void increaseMinute(View view) {
    if (minutes + 1 > 59) {
      minutes = 0;
    } else {
      minutes++;
    }

    updateCounters();
  }

  public void decreaseHour(View view) {
    if (hours - 1 < 0) {
      hours = 23;
    } else {
      hours--;
    }

    updateCounters();
  }

  public void decreaseMinute(View view) {
    if (minutes - 1 < 0) {
      minutes = 59;
    } else {
      minutes--;
    }

    updateCounters();
  }

  /** Saves the user's desired wake time. */
  public void setWakeTime(View view) {
    val newWakeTime = TimeUtils.parseHoursAndMinutesToLocalTime(hours, minutes);
    userProfileManager.setWakeTime(newWakeTime);
    currentWakeTimeDisplay.setText(String.format("Wake time: %s", newWakeTime.toString()));
  }

  /** Saves the user's desired bed time. */
  public void setBedTime(View view) {
    val newBedTime = TimeUtils.parseHoursAndMinutesToLocalTime(hours, minutes);
    userProfileManager.setBedTime(newBedTime);
    currentBedTimeDisplay.setText(String.format("Bed time: %s", newBedTime.toString()));
  }

  /** Updates the UI counters. */
  @SuppressLint("SetTextI18n")
  private void updateCounters() {
    hourDisplay.setText(Integer.toString(hours));
    minuteDisplay.setText(Integer.toString(minutes));
  }
}
