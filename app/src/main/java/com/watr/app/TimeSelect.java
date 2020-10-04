/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.activities.MainActivity;
import lombok.val;

public class TimeSelect extends AppCompatActivity {
  private UserProfileManager userProfileManager;
  private TextView hourDisplay;
  private TextView minuteDisplay;
  private int hours;
  private int minutes;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeselect);

    userProfileManager = MainActivity.getUserProfileManager();
    hourDisplay = findViewById(R.id.hourDisplay);
    minuteDisplay = findViewById(R.id.minuteDisplay);

    val wakeTime = userProfileManager.getWakeTime();
    val wakeHour = wakeTime.getHour();
    val wakeMinute = wakeTime.getMinute();

    hours = wakeHour;
    minutes = wakeMinute;

    hourDisplay.setText(Integer.toString(wakeHour));
    minuteDisplay.setText(Integer.toString(wakeMinute));
  }

  @SuppressLint("SetTextI18n")
  private void updateCounters () {
    hourDisplay.setText(Integer.toString(hours));
    minuteDisplay.setText(Integer.toString(minutes));
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
      hours = 0;
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
}
