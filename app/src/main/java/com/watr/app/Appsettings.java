/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.ui.activities.MainActivity;

public class Appsettings extends AppCompatActivity {
  private SettingsManager settingsManager;
  private Switch ding;
  private Switch ding2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    settingsManager = MainActivity.getSettingsManager();
    setContentView(R.layout.activity_appsettings);
    ding = (Switch) findViewById(R.id.switch3);
    ding2 = (Switch) findViewById(R.id.switch4);
    ding.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    ding2.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));
    Log.d("testi", String.valueOf(settingsManager.getCtx().getBoolean("useMetricUnits", true)));
    ding.setOnCheckedChangeListener(
        new OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            settingsManager.addBoolean("useMetricUnits", isChecked);
          }
        });
    ding2.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));
  }
}
