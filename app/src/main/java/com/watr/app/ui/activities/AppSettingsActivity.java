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

public class AppSettingsActivity extends AppCompatActivity {
  private SettingsManager settingsManager;
  private Switch ding;
  private Switch ding2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appsettings);
    Objects.requireNonNull(getSupportActionBar()).hide();

    settingsManager = MainActivity.getSettingsManager();

    ding = findViewById(R.id.useMetricSwitch);
    ding2 = findViewById(R.id.use24HourClockSwitch);

    ding.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    ding2.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));

    ding.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("useMetricUnits", isChecked));
    ding2.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));
  }
}
