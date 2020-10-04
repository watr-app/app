/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import com.watr.app.constants.Gender;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;

/**
 * Editting user profile settings after first launch happens here
 * @author panueronen
 * @version 1.0.0
 */
public class UserProfileSettings extends AppCompatActivity {
  private UserProfileManager userProfileManager;
  private RadioGroup genderSelectorRadioGroup;
  private EditText dailyTargetInput;

  /**
   *holds radio buttons for gender and buttons for navigating to timeselect.
   *changing daily hydration target also happens from here
   */
  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userProfileManager = MainActivity.getUserProfileManager();
    setContentView(R.layout.activity_user_profile_settings);

    genderSelectorRadioGroup = findViewById(R.id.genderSelectorRadioGroup);
    dailyTargetInput = findViewById(R.id.dailyTargetInput);

    // Picks users gender so it is chosen by default
    if (userProfileManager.getGender() == Gender.MALE) {
      genderSelectorRadioGroup.check(R.id.buttonMale);
    } else {
      genderSelectorRadioGroup.check(R.id.buttonFemale);
    }

    dailyTargetInput.setText(Integer.toString(userProfileManager.getDailyTarget()));

    // Listener that swaps the gender
    genderSelectorRadioGroup.setOnCheckedChangeListener(
        (group, checkedId) -> {
          Gender gender;

          switch (checkedId) {
            case R.id.buttonMale:
              gender = Gender.MALE;

              userProfileManager.setGender(gender);
              userProfileManager.setDailyTarget(gender.getDefaultDailyTarget());
              dailyTargetInput.setText(Integer.toString(userProfileManager.getDailyTarget()));
              break;
            case R.id.buttonFemale:
              gender = Gender.FEMALE;

              userProfileManager.setGender(gender);
              userProfileManager.setDailyTarget(gender.getDefaultDailyTarget());
              dailyTargetInput.setText(Integer.toString(userProfileManager.getDailyTarget()));
              break;
          }
        });
  }

  public void confirmDailyTarget(View view) {
    userProfileManager.setDailyTarget(Integer.parseInt(String.valueOf(dailyTargetInput.getText())));
  }

  public void goToTimeEditor(View view) {
    Intent nextActivity = new Intent(this, TimeSelect.class);
    startActivity(nextActivity);
  }
}
