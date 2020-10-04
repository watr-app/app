/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import com.watr.app.constants.Gender;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import java.util.Objects;


public class CreateProfile extends AppCompatActivity {

  public EditText dailyTarget;
  public int a;
  private SharedPreferences context;
  private SettingsManager settingsManager;
  private UserProfileManager userProfileManager;
  private Switch aSwitch;
  private Switch bSwitch;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userProfileManager = MainActivity.getUserProfileManager();
    settingsManager = MainActivity.getSettingsManager();
    setContentView(R.layout.activity_set_profile);
    RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
    Objects.requireNonNull(getSupportActionBar()).hide();

    // EditText dailyTarget = findViewById(R.id.dailytarget);

    // boolean metric units and 24h clock
    aSwitch = findViewById(R.id.switch5);
    bSwitch = findViewById(R.id.switch6);
    aSwitch.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    bSwitch.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));
    bSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));

    aSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("useMetricUnits", isChecked));

    // set gender and daily target
    rg.check(R.id.gendermale); // <-
    rg.setOnCheckedChangeListener(
        (group, checkedId) -> {
          switch (checkedId) {
            case R.id.gendermale:
              userProfileManager.setGender(Gender.MALE);
              userProfileManager.setDailyTarget(2700);
              break;
            case R.id.genderfemale:
              userProfileManager.setGender(Gender.FEMALE);
              userProfileManager.setDailyTarget(3700);
              break;
          }
        });

    //go back to MainActivity
    final Button button = findViewById(R.id.buttonOk);
    button.setOnClickListener(v -> finish());
  }
}