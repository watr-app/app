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
import com.watr.app.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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
  private static final int ANIMATION_DURATION = 1000;
  private static final int ANIMATION_START_OFFSET = 500;

  // Manager classes
  private final SettingsManager settingsManager;
  private final UserProfileManager userProfileManager;

  // Abstract components
  private MainViewModel mainViewModel;
  private View view;
  private List<HydrationEntity> hydrationRecords;
  private HydrationEntity latestHydrationRecord;

  // Hydration progress
  private TextView currentHydrationAmountDisplay;
  private TextView targetHydrationAmountDisplay;
  private ProgressBar hydrationProgressBar;

  // Contextual message and last ingestion time display
  private TextView contextMessageDisplay;
  private TextView lastIngestionMessageDisplay;

  // Mascot
  private ImageView mascotLeftEye;
  private ImageView mascotRightEye;
  private ImageView mascotMouth;

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
    contextMessageDisplay = view.findViewById(R.id.contextMessage);
    lastIngestionMessageDisplay = view.findViewById(R.id.lastIngestionMessage);

    // Mascot
    mascotLeftEye = view.findViewById(R.id.statusMascotLeftEye);
    mascotRightEye = view.findViewById(R.id.statusMascotRightEye);
    mascotMouth = view.findViewById(R.id.statusMascotMouth);

    // Action button and associated hint
    newHydrationRecordButton = view.findViewById(R.id.newHydrationRecordButton);
    newHydrationRecordButtonHint = view.findViewById(R.id.newHydrationRecordButtonHint);

    // Set action button click listener
    newHydrationRecordButton.setOnClickListener(
        v -> {
          val intent = new Intent(getContext(), NewHydrationRecordActivity.class);
          startActivityForResult(intent, NEW_HYDRATION_RECORD_REQUEST_CODE);
        });

    // Start LiveData observer
    mainViewModel
        .getAllHydrationRecords()
        .observe(getViewLifecycleOwner(), this::setHydrationRecords);
  }

  @SneakyThrows
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == HomePage.NEW_HYDRATION_RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
      val drinkType =
          StringifyUtils.gson.fromJson(
              data.getStringExtra(NewHydrationRecordActivity.REPLY_DRINK_TYPE), DrinkType.class);
      val amount = data.getIntExtra(NewHydrationRecordActivity.REPLY_DRINK_AMOUNT, 1);

      // BUG: For reasons as to why relative hydration is set to 0 here, see HydrationEntity.java
      mainViewModel.insert(new HydrationEntity(new Date(), drinkType, amount, 0));
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
  }

  /**
   * Filters hydration records to only those that were recorded during the current/latest wake *
   * period.
   *
   * @param hydrationRecords {@link List}<{@link HydrationEntity}>
   * @return {@link List}<{@link HydrationEntity}> of the hydration records recorded during the
   *     current/latest wake period.
   */
  @SneakyThrows
  private List<HydrationEntity> filterHydrationRecordsForWakePeriod(
      List<HydrationEntity> hydrationRecords) {
    val activityPeriod = ActivityPeriodManager.getCurrentActivityPeriodWithOffsets();
    val userWakeTime = userProfileManager.getWakeTime();

    // Only show items for current/most recent wake activity period
    return hydrationRecords.stream()
        .filter(
            record ->
                record.getTimestamp().getTime()
                    > TimeUtils.localTimeToUnixTimestamp(
                        userWakeTime, activityPeriod.wakeTimeOffset))
        .collect(Collectors.toList());
  }

  /**
   * Updates the hydration record cache and updates UI values that need to derive information from
   * it.
   *
   * @param hydrationRecords {@link List}<{@link HydrationEntity}>
   */
  @SneakyThrows
  private void setHydrationRecords(List<HydrationEntity> hydrationRecords) {
    // Filter hydration records to only those for the current wake period
    val filteredRecords = filterHydrationRecordsForWakePeriod(hydrationRecords);

    this.hydrationRecords = filteredRecords;
    this.latestHydrationRecord = filteredRecords.size() > 0 ? filteredRecords.get(0) : null;

    if (this.hydrationRecords.size() > 0 && this.latestHydrationRecord != null) {
      // Get hydration target
      val hydrationTarget = userProfileManager.getDailyTarget();

      // Get current total relative hydration
      val currentHydration =
          filteredRecords.stream()
              .reduce(0, (curr, acc) -> curr + acc.getRelativeHydration(), Integer::sum);

      // Check if target has been reached
      val targetHasBeenReached = currentHydration >= hydrationTarget;

      // Get progress towards target
      // Convert to double, divide to get multiplier, then multiply by 100 to get percentage value
      val progressPercentage = (double) currentHydration / (double) hydrationTarget * 100;

      // Get current activity period
      val currentActivityPeriod = ActivityPeriodManager.getCurrentActivityPeriod();

      // Get timestamp of last hydration and calculate how long ago it was
      val lastHydrationTimestamp = latestHydrationRecord.getTimestamp();
      val timeSinceLastHydration =
          TimeUtils.getUnixTimestampDiff(new Date().getTime(), lastHydrationTimestamp.getTime());

      updateHydrationProgress(currentHydration, hydrationTarget, progressPercentage);
      updateContextMessage(currentActivityPeriod, timeSinceLastHydration, targetHasBeenReached);
      updateLastIngestionDisplay(lastHydrationTimestamp);
      updateMascot(currentActivityPeriod, timeSinceLastHydration, targetHasBeenReached);
    }
  }

  /**
   * Updates the hydration progress bar based on how the user is tracking towards their daily
   * target.
   *
   * @param currentHydration {@link Integer} Current relative hydration amount
   * @param hydrationTarget {@link Integer} User daily target
   * @param progressPercentage {@link Double} Percentage of target reached
   */
  @SuppressLint("DefaultLocale")
  private void updateHydrationProgress(
      int currentHydration, int hydrationTarget, double progressPercentage) {

    // Determine if we need to use metric or imperial units
    val useMetricUnits = settingsManager.getCtx().getBoolean("useMetricUnits", true);

    // Determine visual representation of unit
    val unit = useMetricUnits ? "ml" : "fl.oz";

    val current =
        useMetricUnits
            ? currentHydration
            : NumberUtils.convertMillilitresToFluidOunces(currentHydration);

    val target =
        useMetricUnits
            ? hydrationTarget
            : NumberUtils.convertMillilitresToFluidOunces(hydrationTarget);

    currentHydrationAmountDisplay.setText(String.format("%d %s", (int) current, unit));
    targetHydrationAmountDisplay.setText(String.format("%d %s", (int) target, unit));

    hydrationProgressBar.setProgress(
        NumberUtils.doubleToInteger(NumberUtils.normalisePercentage(progressPercentage, 100)));
  }

  /**
   * Updates context message based on how user is tracking towards their daily target.
   *
   * @param currentActivityPeriod {@link ActivityPeriod} Current activity period
   * @param timeSinceLastHydration {@link Long} Time in milliseconds since last hydration record
   * @param targetHasBeenReached {@link Boolean} Whether daily target has been reached
   */
  private void updateContextMessage(
      ActivityPeriod currentActivityPeriod,
      long timeSinceLastHydration,
      boolean targetHasBeenReached) {
    val hoursSinceLastHydration = TimeUnit.MILLISECONDS.toHours(timeSinceLastHydration);

    if (currentActivityPeriod == ActivityPeriod.ASLEEP) {
      contextMessageDisplay.setText(R.string.context_message_asleep);
    } else if (targetHasBeenReached) {
      contextMessageDisplay.setText(R.string.context_message_hydration_target_achieved);
    } else if (hoursSinceLastHydration >= 1) {
      contextMessageDisplay.setText(R.string.context_message_hydration_forgotten);
    } else {
      contextMessageDisplay.setText(R.string.context_message_hydration_remembered);
    }
  }

  /**
   * Updates the time since the last hydration record.
   *
   * @param lastHydrationTimestamp {@link Date} Timestamp of latest hydration record
   */
  private void updateLastIngestionDisplay(Date lastHydrationTimestamp) {
    lastIngestionMessageDisplay.setText(
        String.format(
            "Last fluid ingestion: %s", TimeUtils.prettyTime.format(lastHydrationTimestamp)));
  }

  /**
   * Updates the mascot's appearance based on how user is tracking towards their daily target.
   *
   * @param currentActivityPeriod {@link ActivityPeriod} Current activity period
   * @param timeSinceLastHydration {@link Long} Time in milliseconds since last hydration record
   * @param targetHasBeenReached {@link Boolean} Whether daily target has been reached
   */
  private void updateMascot(
      ActivityPeriod currentActivityPeriod,
      long timeSinceLastHydration,
      boolean targetHasBeenReached) {
    val hoursSinceLastHydration = TimeUnit.MILLISECONDS.toHours(timeSinceLastHydration);

    if (currentActivityPeriod == ActivityPeriod.AWAKE
        && !targetHasBeenReached
        && hoursSinceLastHydration >= 1) {
      // Point mouth down (Sad)
      mascotMouth.setRotation(270f);
    } else {
      // Point mouth up (Happy)
      mascotMouth.setRotation(90f);
    }
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
            ANIMATION_DURATION, ANIMATION_START_OFFSET, mascotLeftEye, leftEyeAnimations);
    leftEyeAnimation.start();

    val rightEyeAnimation =
        new ImageViewAnimator(
            ANIMATION_DURATION, ANIMATION_START_OFFSET, mascotRightEye, rightEyeAnimations);
    rightEyeAnimation.start();
  }
}
