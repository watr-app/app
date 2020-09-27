package com.watr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.watr.app.datastore.settings.SettingsManager;

public class MainActivity extends AppCompatActivity {

  private SettingsManager prefmanager = new SettingsManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
