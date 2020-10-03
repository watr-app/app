/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.pages;

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
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * Settings page. All functionality specific to the settings page goes here.
 *
 * @author panueronen
 * @version 1.0.0
 */
@NoArgsConstructor
public class SettingsPage extends Fragment {
  private View view;
  List<String> settings = new ArrayList<String>();


  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    settings.add("Metric System");
    settings.add("24h Clock");
    settings.add("Edit user profile");
    this.view = view;
    ListView lv = view.findViewById(R.id.settingslist);
    lv.setAdapter(new ArrayAdapter<String>(
        getContext(),android.R.layout.simple_list_item_1,
        settings
    ));

    }


}
