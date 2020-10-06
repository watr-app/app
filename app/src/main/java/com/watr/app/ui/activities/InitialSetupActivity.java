/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.watr.app.R;
import com.watr.app.constants.Gender;
import com.watr.app.datastore.sharedpreferences.settings.SettingsManager;
import com.watr.app.datastore.sharedpreferences.userprofile.UserProfileManager;
import com.watr.app.ui.utils.ButtonStateToggler;
import com.watr.app.ui.utils.NumberInputValueCoercer;
import com.watr.app.utils.InputUtils;
import com.watr.app.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.val;

/**
 * Initial setup activity.
 *
 * @author mhgitti
 * @version 1.0.0
 */
@SuppressLint("UseSwitchCompatOrMaterialCode")
public class InitialSetupActivity extends AppCompatActivity {
  private SettingsManager settingsManager;
  private UserProfileManager userProfileManager;
  private Switch metricSwitch;
  private Switch clockFormatSwitch;
  private RadioGroup genderSelector;
  private EditText wakeHourInput;
  private EditText wakeMinuteInput;
  private EditText sleepHourInput;
  private EditText sleepMinuteInput;
  private Button confirmButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_profile);
    Objects.requireNonNull(getSupportActionBar()).hide();

    userProfileManager = MainActivity.getUserProfileManager();
    settingsManager = MainActivity.getSettingsManager();

    // Unit and clock format, gender selector
    metricSwitch = findViewById(R.id.setupMetricSwitch);
    clockFormatSwitch = findViewById(R.id.setupClockSwitch);
    genderSelector = findViewById(R.id.setupGenderSelector);

    // Input fields
    wakeHourInput = findViewById(R.id.wakeHourInput);
    wakeMinuteInput = findViewById(R.id.wakeMinuteInput);
    sleepHourInput = findViewById(R.id.sleepHourInput);
    sleepMinuteInput = findViewById(R.id.sleepMinuteInput);

    // Confirm button
    confirmButton = findViewById(R.id.setupConfirmButton);

    setFieldDefaults();
    addEventListeners();
  }

  /** Sets default values for setup fields. */
  @SuppressLint("SetTextI18n")
  private void setFieldDefaults() {
    // Units and clock
    metricSwitch.setChecked(settingsManager.getCtx().getBoolean("useMetricUnits", true));
    clockFormatSwitch.setChecked(settingsManager.getCtx().getBoolean("use24HrClock", true));

    // Gender selector
    genderSelector.check(R.id.setupGenderMale);

    // Wake and bed time inputs

    val wakeTime = userProfileManager.getWakeTime();
    val bedTime = userProfileManager.getBedTime();

    wakeHourInput.setText(Integer.toString(wakeTime.getHour()));
    wakeMinuteInput.setText(Integer.toString(wakeTime.getMinute()));

    sleepHourInput.setText(Integer.toString(bedTime.getHour()));
    sleepMinuteInput.setText(Integer.toString(bedTime.getMinute()));
  }

  /** Adds event listeners to input controls. */
  private void addEventListeners() {
    // Switch change listeners
    metricSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("useMetricUnits", isChecked));
    clockFormatSwitch.setOnCheckedChangeListener(
        (buttonView, isChecked) -> settingsManager.addBoolean("use24HrClock", isChecked));

    // Gender radio group change listener
    genderSelector.setOnCheckedChangeListener(
        (group, checkedId) -> {
          Gender gender;

          switch (checkedId) {
            case R.id.setupGenderMale:
              gender = Gender.MALE;
              break;
            case R.id.setupGenderFemale:
              gender = Gender.FEMALE;
              break;
            default:
              throw new IllegalStateException(
                  String.format(
                      "Received unexpected gender choice: Expected %d for male or %d for female, but got %d",
                      R.id.setupGenderMale, R.id.setupGenderFemale, checkedId));
          }

          userProfileManager.setGender(gender);
          userProfileManager.setDailyTarget(gender.getDefaultDailyTarget());
        });

    // Hour / minute input value coercers
    wakeHourInput.addTextChangedListener(new NumberInputValueCoercer(wakeHourInput, 0, 23));
    wakeMinuteInput.addTextChangedListener(new NumberInputValueCoercer(wakeMinuteInput, 0, 59));
    sleepHourInput.addTextChangedListener(new NumberInputValueCoercer(sleepHourInput, 0, 23));
    sleepMinuteInput.addTextChangedListener(new NumberInputValueCoercer(sleepMinuteInput, 0, 59));
  }

  /**
   * Click handler for user clicking the confirmation button.
   *
   * @param view {@link View}
   */
  public void onConfirm(View view) {
    val inputs = new ArrayList<EditText>();

    inputs.add(wakeHourInput);
    inputs.add(wakeMinuteInput);
    inputs.add(sleepHourInput);
    inputs.add(sleepMinuteInput);

    // Having to use an AtomicInteger here since lambda variables should be (effectively) final
    val invalidInputAmount = new AtomicInteger();

    // Validate that inputs are non-empty
    inputs.forEach(
        input -> {
          val isInvalid = InputUtils.inputIsEmpty(input);

          if (isInvalid) {
            invalidInputAmount.getAndIncrement();
            toggleInputErrorState(input, true);
          } else {
            toggleInputErrorState(input, false);
          }
        });

    // If there were no invalid inputs, we're good to go here
    if (invalidInputAmount.get() == 0) {
      val wakeTime =
          TimeUtils.parseHoursAndMinutesToLocalTime(
              InputUtils.getInteger(wakeHourInput), InputUtils.getInteger(wakeMinuteInput));
      val bedTime =
          TimeUtils.parseHoursAndMinutesToLocalTime(
              InputUtils.getInteger(sleepHourInput), InputUtils.getInteger(sleepMinuteInput));

      // Update profile values
      userProfileManager.setWakeTime(wakeTime);
      userProfileManager.setBedTime(bedTime);

      // Ensure setup does not run again
      settingsManager.addBoolean("needsSetup", false);
      finish();
    }
  }

  /**
   * Toggles a number input's error state without rewriting the field value unnecessarily to save
   * memory.
   *
   * @param inputField {@link EditText} Input field
   * @param hasError {@link Boolean} Whether input content is invalid
   */
  private void toggleInputErrorState(EditText inputField, boolean hasError) {
    if (hasError) {
      if (inputField.getError() == null) { // Only set error if there isn't one already
        inputField.setError("This field must not be empty!");
      }

      if (confirmButton.isEnabled()) { // Only disable button if isn't disabled already
        ButtonStateToggler.disableButton(confirmButton);
      }
    } else {
      if (inputField.getError() != null) { // Only unset error if there is one already
        inputField.setError(null);
      }

      if (confirmButton.isEnabled()) { // Only re-enable button if isn't enabled already
        ButtonStateToggler.enableButton(confirmButton);
      }
    }
  }
}
