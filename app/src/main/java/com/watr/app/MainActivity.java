package com.watr.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.watr.app.hydration.UserSharedPreferenceManager;

public class MainActivity extends AppCompatActivity {

  private UserSharedPreferenceManager prefmanager = new UserSharedPreferenceManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
