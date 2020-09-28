/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import lombok.RequiredArgsConstructor;

/**
 * View pager fragment page skeleton. Dynamically selects a layout for the fragment page based on a
 * provided layout ID.
 */
@RequiredArgsConstructor
public class FragmentPage extends Fragment {
  private final int layoutId;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(layoutId, container, false);
  }
}
