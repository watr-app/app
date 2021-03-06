/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup.Input;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.watr.app.R;
import com.watr.app.constants.DrinkType;
import com.watr.app.ui.utils.ButtonStateToggler;
import com.watr.app.utils.InputUtils;
import com.watr.app.utils.StringifyUtils;
import java.util.Objects;
import lombok.NonNull;
import lombok.val;

/**
 * Hydration record addition activity.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class NewHydrationRecordActivity extends AppCompatActivity {
  public static final String REPLY_DRINK_TYPE = "com.watr.app.newhydrationrecord.REPLY_DRINK_TYPE";
  public static final String REPLY_DRINK_AMOUNT =
      "com.watr.app.newhydrationrecord.REPLY_DRINK_AMOUNT";

  private Spinner drinkTypeDropdown;
  private EditText drinkAmountInput;
  private FloatingActionButton saveButton;
  private TextView saveButtonHint;

  private DrinkType selectedDrinkType;

  private boolean userHasChangedInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_hydration_record);

    // Hide action bar
    Objects.requireNonNull(getSupportActionBar()).hide();

    // Init
    drinkTypeDropdown = findViewById(R.id.ingestedLiquidTypeInput);
    drinkAmountInput = findViewById(R.id.ingestedLiquidAmount);
    saveButton = findViewById(R.id.saveHydrationRecordButton);
    saveButtonHint = findViewById(R.id.saveHydrationRecordHint);

    // Create SpinnerAdapter
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(
            this, R.array.drink_types_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    drinkTypeDropdown.setAdapter(adapter);

    // Set dropdown select listener
    drinkTypeDropdown.setOnItemSelectedListener(
        new OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDrinkType = DrinkType.values()[position];
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });

    // Set input change listener
    drinkAmountInput.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            if (!userHasChangedInput) { // Do not complain on activity start
              userHasChangedInput = true;
            } else {
              if (InputUtils.inputIsEmptyOrZero(drinkAmountInput)) {
                ButtonStateToggler.disableButton(saveButton);
                drinkAmountInput.setError("Amount has to be non-zero!");
                saveButtonHint.setText(R.string.hydration_record_save_hint_error);
                saveButtonHint.setTextColor(Color.RED);
              } else {
                ButtonStateToggler.enableButton(saveButton);
                drinkAmountInput.setError(null);
                saveButtonHint.setText(R.string.hydration_record_save_hint);
                saveButtonHint.setTextColor(Color.GRAY);
              }
            }
          }
        });

    // Set save button click listener
    saveButton.setOnClickListener(
        v -> {
          val replyIntent = new Intent();

          replyIntent.putExtra(REPLY_DRINK_TYPE, StringifyUtils.gson.toJson(selectedDrinkType));
          replyIntent.putExtra(REPLY_DRINK_AMOUNT, InputUtils.getInteger(drinkAmountInput));
          setResult(RESULT_OK, replyIntent);

          finish();
        });
  }
}
