/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.activities.MainActivity;

public class timeselect extends AppCompatActivity {
  private int mm = 10;
  private int hh = 10;
  private SettingsManager userProfileManager;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeselect);
    userProfileManager = MainActivity.getSettingsManager();
    //Log.d(userProfileManager.getCtx().getBoolean("useMetricUnits",true));
    /*
    TextView tv1 = findViewById(R.id.mmbox);
    TextView tv2 = findViewById(R.id.hhbox);
    tv1.setText(Integer.toString(mm));
    tv2.setText(Integer.toString(hh));*/
  }
  private void increasehh(View view){
    if(hh + 1>23){
      hh = 0;
    }
      hh = hh+1;
  }
  private void increasemm(View view){
    if(mm +1 > 59){
      mm= mm+1;
    }else{
      mm = 0;
    }

  }
  private void decreasehh(View view){
    if(hh - 1 <0){
      hh = 24;
    }else{
    hh = hh-1;
    }
  }
  private void decreasemm(View view){
    if(mm -1 <0){
      mm = 59;
    }else{
    mm = mm -1;
        }
  }
}