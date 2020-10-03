/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.pages;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.watr.app.R;
import com.watr.app.constants.ActivityPeriod;
import com.watr.app.constants.DrinkType;
import com.watr.app.datastore.room.hydration.HydrationEntity;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.timemgmt.ActivityPeriodManager;
import com.watr.app.timemgmt.UnknownTimeIntervalException;
import com.watr.app.ui.activities.MainActivity;
import com.watr.app.ui.activities.NewHydrationRecordActivity;
import com.watr.app.ui.utils.ButtonStateToggler;
import com.watr.app.ui.utils.ImageViewAnimator;
import com.watr.app.ui.viewmodels.MainViewModel;
import com.watr.app.utils.NumberUtils;
import com.watr.app.utils.StringifyUtils;
import java.util.ArrayList;
import java.util.Date;
import lombok.SneakyThrows;
import lombok.val;

/**
 * Home page. All functionality specific to the home page goes here.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class HomePage extends Fragment {
  // Constants
  public static final int NEW_HYDRATION_RECORD_REQUEST_CODE = 1;
  private static final int ANIMATION_DURATION = 1500;
  private static final int ANIMATION_START_OFFSET = 500;

  // Manager classes
  private final SettingsManager settingsManager;
  private final UserProfileManager userProfileManager;

  // Abstract components
  private MainViewModel mainViewModel;
  private View view;

  // Hydration progress
  private TextView currentHydrationAmountDisplay;
  private TextView targetHydrationAmountDisplay;
  private ProgressBar hydrationProgressBar;

  // Contextual message and last ingestion time display
  private TextView contextualMessageDisplay;
  private TextView lastIngestionMessageDisplay;

  // Mascot
  private ImageView leftEye;
  private ImageView rightEye;

  // Action button and associated hint
  private FloatingActionButton newHydrationRecordButton;
  private TextView newHydrationRecordButtonHint;

  // Animations
  private ArrayList<TranslateAnimation> leftEyeAnimations = new ArrayList<>();
  private ArrayList<TranslateAnimation> rightEyeAnimations = new ArrayList<>();

  public HomePage() {
    settingsManager = MainActivity.getSettingsManager();
    userProfileManager = MainActivity.getUserProfileManager();

    // Developer note: DO NOT try to optimise below by cloning arrays etc.
    // For some reason, if sourced from the same ArrayList, the animation set that was defined first
    // will stop after the first animation, and no known method of cloning appears to solve it.
    // The *ONLY* way to get the animations to work properly is to do what is done below, which
    // unfortunately happens to be the complete antithesis of DRY.
    // Pretty it ain't, but hey, problem solved.

    leftEyeAnimations.add(new TranslateAnimation(0, 10, 0, 10));
    leftEyeAnimations.add(new TranslateAnimation(10, -10, 10, 10));
    leftEyeAnimations.add(new TranslateAnimation(-10, 10, 10, -10));
    leftEyeAnimations.add(new TranslateAnimation(10, -10, -10, -10));
    leftEyeAnimations.add(new TranslateAnimation(-10, 0, -10, 0));

    rightEyeAnimations.add(new TranslateAnimation(0, 10, 0, 10));
    rightEyeAnimations.add(new TranslateAnimation(10, -10, 10, 10));
    rightEyeAnimations.add(new TranslateAnimation(-10, 10, 10, -10));
    rightEyeAnimations.add(new TranslateAnimation(10, -10, -10, -10));
    rightEyeAnimations.add(new TranslateAnimation(-10, 0, -10, 0));
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Abstract components
    mainViewModel = MainActivity.getMainViewModel();
    this.view = view;

    // Hydration progress
    currentHydrationAmountDisplay = view.findViewById(R.id.ingestedLiquidAmount);
    targetHydrationAmountDisplay = view.findViewById(R.id.targetLiquidAmount);
    hydrationProgressBar = view.findViewById(R.id.hydrationProgressBar);

    // Contextual message and last ingestion time display
    contextualMessageDisplay = view.findViewById(R.id.contextualMessage);
    lastIngestionMessageDisplay = view.findViewById(R.id.lastIngestionMessage);

    // Mascot
    leftEye = view.findViewById(R.id.statusMascotLeftEye);
    rightEye = view.findViewById(R.id.statusMascotRightEye);

    // Action button and associated hint
    newHydrationRecordButton = view.findViewById(R.id.newHydrationRecordButton);
    newHydrationRecordButtonHint = view.findViewById(R.id.newHydrationRecordButtonHint);

    newHydrationRecordButton.setOnClickListener(
        v -> {
          val intent = new Intent(getContext(), NewHydrationRecordActivity.class);
          startActivityForResult(intent, NEW_HYDRATION_RECORD_REQUEST_CODE);
        });
  }

  @SneakyThrows
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == HomePage.NEW_HYDRATION_RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
      val drinkType =
          StringifyUtils.gson.fromJson(
              data.getStringExtra(NewHydrationRecordActivity.REPLY_DRINK_TYPE), DrinkType.class);
      val amount = data.getIntExtra(NewHydrationRecordActivity.REPLY_DRINK_AMOUNT, 1);

      mainViewModel.insert(new HydrationEntity(new Date(), drinkType, amount));
    } else {
      Toast.makeText(getContext(), "Hydration record not saved!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    // Start eye animations
    startEyeAnimations();

    // Set runtime values
    updateActionButtonState();
    updateHydrationProgress();
  }

  @SuppressLint("DefaultLocale")
  private void updateHydrationProgress() {
    val unit = settingsManager.getCtx().getBoolean("useMetricUnits", true) ? "ml" : "fl.oz";
    val currentHydration = 0; // TODO: Get from DB
    val hydrationTarget = userProfileManager.getDailyTarget();

    currentHydrationAmountDisplay.setText(String.format("%d %s", currentHydration, unit));
    targetHydrationAmountDisplay.setText(String.format("%d %s", hydrationTarget, unit));
    hydrationProgressBar.setProgress(
        NumberUtils.normalisePercentage(currentHydration / hydrationTarget, 100));
  }

  /** Updates action button state and associated hint based on user's current activity period. */
  private void updateActionButtonState() {
    try {
      if (ActivityPeriodManager.getCurrentActivityPeriod() == ActivityPeriod.ASLEEP) {
        ButtonStateToggler.disableButton(newHydrationRecordButton);
        newHydrationRecordButtonHint.setText(R.string.home_page_action_button_hint_asleep);
      } else {
        ButtonStateToggler.enableButton(newHydrationRecordButton);
        newHydrationRecordButtonHint.setText(R.string.home_page_action_button_hint_awake);
      }
    } catch (UnknownTimeIntervalException e) {
      Log.e(
          "home-page",
          "Could not adjust action button state due to an error in determining current activity period: ",
          e);
    }
  }

  /** Starts mascot eye animations. */
  private void startEyeAnimations() {
    val leftEyeAnimation =
        new ImageViewAnimator(
            ANIMATION_DURATION, ANIMATION_START_OFFSET, leftEye, leftEyeAnimations);
    leftEyeAnimation.start();

    val rightEyeAnimation =
        new ImageViewAnimator(
            ANIMATION_DURATION, ANIMATION_START_OFFSET, rightEye, rightEyeAnimations);
    rightEyeAnimation.start();
  }
}
