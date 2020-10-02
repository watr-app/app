/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import java.util.Objects;

public class NewHydrationRecordActivity extends AppCompatActivity {
  public static final String REPLY = "com.watr.app.newhydrationrecord.REPLY";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_hydration_record);
    Objects.requireNonNull(getSupportActionBar()).hide();
  }
}
