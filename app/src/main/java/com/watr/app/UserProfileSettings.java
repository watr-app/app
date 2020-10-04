/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.watr.app.constants.Gender;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.activities.MainActivity;
import lombok.ToString;

public class UserProfileSettings extends AppCompatActivity {
  private UserProfileManager userProfileManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userProfileManager = MainActivity.getUserProfileManager();
    setContentView(R.layout.activity_user_profile_settings);
    RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

    // Picks users gender so it is chosen by default
    if (userProfileManager.getGender() == Gender.MALE) {
      rg.check(R.id.gendermale);
    } else {
      rg.check(R.id.genderfemale);
    }
    TextView tv = findViewById(R.id.dailytargetbox);
    tv.setText(Integer.toString(userProfileManager.getDailyTarget()));


    // Listener that swaps he gender
    rg.setOnCheckedChangeListener(
        new OnCheckedChangeListener() {
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
              case R.id.gendermale:
                userProfileManager.setGender(Gender.MALE);
                break;
              case R.id.genderfemale:
                userProfileManager.setGender(Gender.FEMALE);
                break;
            }
          }
        });
  }
  public void confirmdailytarget(View view) {
    TextView tv = findViewById(R.id.dailytargetbox);
    userProfileManager.setDailyTarget(Integer.parseInt(String.valueOf(tv.getText())));
  }
  public void gototimeedit(View view){
    Intent nextActivity = new Intent(this, timeselect.class);
    startActivity(nextActivity);
  }

}
