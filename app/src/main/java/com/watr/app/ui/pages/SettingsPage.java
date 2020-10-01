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
import androidx.fragment.app.Fragment;
import com.watr.app.R;
import lombok.NoArgsConstructor;

/**
 * Settings page. All functionality specific to the settings page goes here.
 *
 * @author panueronen
 * @version 1.0.0
 */
@NoArgsConstructor
public class SettingsPage extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }
}
