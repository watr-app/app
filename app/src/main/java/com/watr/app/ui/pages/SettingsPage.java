/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.watr.app.AppSettings;
import com.watr.app.R;
import com.watr.app.UserProfileSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;

/**
 * Settings page. All functionality specific to the settings page goes here.
 *
 * @author panueronen
 * @version 1.0.0
 */
@NoArgsConstructor
public class SettingsPage extends Fragment {
  List<String> settings = new ArrayList<>();
  private View view;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  /**
   *contains the apps listview.
   * used for navigating to appsettings and user settings.
   */
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    settings.add("App settings");
    settings.add("Edit user profile");

    this.view = view;

    ListView lv = view.findViewById(R.id.settingslist);

    lv.setAdapter(
        new ArrayAdapter<>(
            Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, settings));

    lv.setOnItemClickListener(
        (parent, parentView, position, id) -> {
          if (position == 0) {
            Intent nextActivity = new Intent(getContext(), AppSettings.class);
            startActivity(nextActivity);
          } else {
            Intent nextActivity = new Intent(getContext(), UserProfileSettings.class);
            startActivity(nextActivity);
          }
        });
  }
}
