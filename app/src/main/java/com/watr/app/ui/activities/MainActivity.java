/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.watr.app.R;
import com.watr.app.datastore.sharedpreferences.DataStorePreFlightCheckException;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.pages.HistoryPage;
import com.watr.app.ui.pages.HomePage;
import com.watr.app.ui.pages.SettingsPage;
import com.watr.app.ui.utils.PageTracker;
import com.watr.app.ui.viewmodels.MainViewModel;
import java.util.Objects;
import lombok.Getter;
import lombok.val;

/**
 * Application main activity.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class MainActivity extends AppCompatActivity {

  public static final int DEFAULT_PAGE = 1;
  private static final int PAGE_COUNT = 3;

  @Getter
  private static MainViewModel mainViewModel;
  @Getter
  private static SettingsManager settingsManager;
  @Getter
  private static UserProfileManager userProfileManager;

  private TabLayout navigationBar;
  private ViewPager2 viewPager;

  private PageTracker pageTracker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialise manager classes
    mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    settingsManager = new SettingsManager(getSharedPreferences("settings", Context.MODE_PRIVATE));
    userProfileManager =
        new UserProfileManager(getSharedPreferences("userprofile", Context.MODE_PRIVATE));

    if (settingsManager.getCtx().getBoolean("needsSetup", true)) {
      Intent i = new Intent(MainActivity.this, CreateProfile.class);
      MainActivity.this.startActivity(i);
      settingsManager.addBoolean("needsSetup", true);
    } else {
      try {
        userProfileManager.checkRequiredProfileSettings();
      } catch (DataStorePreFlightCheckException e) {
        Log.e("datastore-preflight", "Data store pre-flight checks failed: ", e);
      }
    }

    // Init
    navigationBar = findViewById(R.id.navigationBar);
    viewPager = findViewById(R.id.viewPager);

    pageTracker = new PageTracker(DEFAULT_PAGE);

    // Hide action bar
    Objects.requireNonNull(getSupportActionBar()).hide();

    // Initialise ViewPager and PagerAdapter, and set current item to the default one one
    viewPager.setAdapter(new PagerAdapter(this));

    // Set current navigation bar item and current page to the default
    // Setting smoothScroll to false to prevent jitter on boot
    val defaultPosition = pageTracker.getCurrentPage();
    updateNavigationBar(defaultPosition);
    updateViewPager(defaultPosition, false);

    // Attaching a tab select listener that triggers the view pager to switch and vice versa here
    // in order to synchro the navigation bar and the view pager

    navigationBar.addOnTabSelectedListener(
        new OnTabSelectedListener() {
          @Override
          public void onTabSelected(Tab tab) {
            updateViewPager(tab.getPosition());
          }

          @Override
          public void onTabUnselected(Tab tab) {
          }

          @Override
          public void onTabReselected(Tab tab) {
          }
        });

    viewPager.registerOnPageChangeCallback(
        new OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {
            super.onPageSelected(position);
            updateNavigationBar(position);
            pageTracker.setCurrentPage(position);
          }
        });
  }

  @Override
  public void onBackPressed() {
    if (pageTracker.currentPageIsFirst(true)) {
      // If at first occurrence of home page, allow system to handle the Back button (Suspend app)
      super.onBackPressed();
    } else {
      // Otherwise, select previous page
      updateViewPager(pageTracker.getPreviousPage());
    }
  }

  /**
   * Sets the navigation bar to the tab at the submitted index.
   *
   * @param position {@link Integer} Tab index
   */
  private void updateNavigationBar(int position) {
    navigationBar.selectTab(navigationBar.getTabAt(position));
  }

  /**
   * Sends the view pager to the page at the submitted index.
   *
   * @param position {@link Integer} Page index
   */
  private void updateViewPager(int position) {
    viewPager.setCurrentItem(position);
  }


  /**
   * Sends the view pager to the page at the submitted index, with a special case for where
   * smoothScroll needs to be overridden.
   *
   * @param position     {@link Integer} Page index
   * @param smoothScroll {@link Boolean} Override for whether to trigger the scroll animation on the
   *                     view pager
   */
  private void updateViewPager(int position, boolean smoothScroll) {
    viewPager.setCurrentItem(position, smoothScroll);
  }


  /**
   * Adapter to create ViewPager2 page fragments based on separately defined Fragment subclasses.
   *
   * @author linuswillner
   * @version 1.0.0
   */
  private static class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(FragmentActivity fragmentActivity) {
      super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int index) {
      switch (index) {
        case 0:
          return new HistoryPage();
        case 2:
          return new SettingsPage();
        case 1:
        default:
          // In case any new pages beyond the 3 initial ones get added later
          return new HomePage();
      }
    }

    @Override
    public int getItemCount() {
      return PAGE_COUNT;
    }
  }


}
