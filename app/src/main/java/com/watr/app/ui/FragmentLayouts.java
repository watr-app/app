/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui;

import com.watr.app.R;
import lombok.RequiredArgsConstructor;

/**
 * List of fragment layouts (I.e. different pages in the application). Used by the FragmentPage
 * class to assign layouts to fragment pages in the ViewPager.
 */
@RequiredArgsConstructor
public enum FragmentLayouts {
  HISTORY(R.layout.fragment_history),
  HOME(R.layout.fragment_home),
  SETTINGS(R.layout.fragment_settings);

  public final int layoutId;
}
