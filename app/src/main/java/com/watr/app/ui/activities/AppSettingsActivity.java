/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.activities;

import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import java.util.Objects;

/**
 * Application settings activity.
 *
 * @author panueronen
 * @version 1.0.0
 */
public class AppSettingsActivity extends AppCompatActivity {
  private SettingsManager settingsManager;
  private Switch metricSwitch;
  private Switch clockFormatSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appsettings);
    Objects.requireNonNull(getSupportActionBar()).hide();

    settingsManager = MainActivity.getSettingsManager();

    metricSwitch = findViewById(R.id.useMetricSwitch);
    clockFormatSwitch = findViewById(R.id.use24HourClockSwitch);

    metricSwitch.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    clockFormatSwitch.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));

    metricSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("useMetricUnits", isChecked));
    clockFormatSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));
  }
}
