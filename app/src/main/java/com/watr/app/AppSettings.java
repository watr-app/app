/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.ui.activities.MainActivity;

public class AppSettings extends AppCompatActivity {
  private SettingsManager settingsManager;
  private Switch ding;
  private Switch ding2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appsettings);

    settingsManager = MainActivity.getSettingsManager();

    ding = findViewById(R.id.switch3);
    ding2 = findViewById(R.id.switch4);

    ding.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    ding2.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));

    ding.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("useMetricUnits", isChecked));
    ding2.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));
  }
}
