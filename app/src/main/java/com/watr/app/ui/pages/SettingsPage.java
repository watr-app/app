/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.watr.app.Appsettings;
import com.watr.app.R;
import com.watr.app.UserProfileSettings;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.activities.MainActivity;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Settings page. All functionality specific to the settings page goes here.
 *
 * @author panueronen
 * @version 1.0.0
 */
@NoArgsConstructor
public class SettingsPage extends Fragment {
  private View view;
  private String opt1,opt2;
  String EXTRA;
  List<String> settings = new ArrayList<String>();



  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    settings.add("App settings");
    settings.add("Edit user profile");
    this.view = view;
    ListView lv = view.findViewById(R.id.settingslist);
    lv.setAdapter(new ArrayAdapter<String>(
        getContext(),android.R.layout.simple_list_item_1,
        settings
    ));
    lv.setOnItemClickListener(
        new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            Log.d("Tag", "onItemClick(" + i + ")");
            if (i == 0) {
              Intent nextActivity = new Intent(getContext(), Appsettings.class);
              nextActivity.putExtra(EXTRA, i);
              startActivity(nextActivity);
            }else{
              Intent nextActivity = new Intent(getContext(), UserProfileSettings.class);
              nextActivity.putExtra(EXTRA, i);
              startActivity(nextActivity);
              
            }
          }
        });
    }


}
