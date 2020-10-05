/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.pages;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.watr.app.R;
import com.watr.app.ui.activities.AppSettingsActivity;
import com.watr.app.ui.activities.UserProfileSettingsActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
  private List<String> settings = new ArrayList<>();
  private View view;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    settings.add("Application settings");
    settings.add("Edit user profile");

    this.view = view;

    ListView settingsListView = view.findViewById(R.id.settingsList);

    // TODO: Optimise items with HashMap

    val adapter =
        new ArrayAdapter<>(
            Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, settings);

    settingsListView.setAdapter(adapter);

    settingsListView.setOnItemClickListener(
        (parent, view1, position, id) -> {
          val context = getContext();
          Intent nextActivity;

          switch (settings.get(position)) {
            case "Application settings":
              nextActivity = new Intent(context, AppSettingsActivity.class);
              break;
            case "Edit user profile":
              nextActivity = new Intent(context, UserProfileSettingsActivity.class);
              break;
            default:
              throw new ActivityNotFoundException(
                  String.format(
                      "Activity for settings field %s not found", settings.get(position)));
          }

          startActivity(nextActivity);
        });
  }
}
