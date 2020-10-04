/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.ui.activities.MainActivity;

public class Appsettings extends AppCompatActivity {
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsManager = MainActivity.getSettingsManager();
        setContentView(R.layout.activity_appsettings);


    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.systemmetric:
                if(checked)
                    settingsManager.addBoolean("useMetricUnits",true);
                Log.d("tag","if = metric");
                break;
            case R.id.systemimperial:
                if(checked)
                    Log.d("tag","if = imperial");
                    settingsManager.addBoolean("useMetricUnits",false);
                break;
        }
        switch (view.getId()){
            case R.id.clock24h:
                if(checked)
                    settingsManager.addBoolean("use24HrClock",true);
                Log.d("tag","if = metric");
                break;
            case R.id.clock12h:
                if(checked)
                    Log.d("tag","if = imperial");
                settingsManager.addBoolean("use24HrClock",false);
                break;
        }
    }
}