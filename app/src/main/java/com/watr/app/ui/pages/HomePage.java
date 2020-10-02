/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.pages;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.watr.app.R;
import com.watr.app.ui.activities.MainActivity;
import com.watr.app.ui.activities.NewHydrationRecordActivity;
import com.watr.app.ui.utils.ImageViewAnimator;
import com.watr.app.ui.viewmodels.MainViewModel;
import java.util.ArrayList;
import lombok.val;

/**
 * Home page. All functionality specific to the home page goes here.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class HomePage extends Fragment {
  public static final int NEW_HYDRATION_RECORD_REQUEST_CODE = 1;

  private static final int ANIMATION_DURATION = 1500;
  private static final int ANIMATION_START_OFFSET = 500;
  private MainViewModel mainViewModel;
  private ImageView leftEye;
  private ImageView rightEye;
  private FloatingActionButton actionButton;
  private View view;

  private ArrayList<TranslateAnimation> leftEyeAnimations = new ArrayList<>();
  private ArrayList<TranslateAnimation> rightEyeAnimations = new ArrayList<>();

  public HomePage() {
    // Developer note: DO NOT try to optimise this by cloning arrays etc.
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
    this.view = view;
    mainViewModel = MainActivity.getMainViewModel();

    leftEye = view.findViewById(R.id.statusMascotLeftEye);
    rightEye = view.findViewById(R.id.statusMascotRightEye);
    actionButton = view.findViewById(R.id.actionButton);

    actionButton.setOnClickListener(
        v -> {
          val intent = new Intent(getContext(), NewHydrationRecordActivity.class);
          startActivityForResult(intent, NEW_HYDRATION_RECORD_REQUEST_CODE);
        });
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d("test", "onActivityResult: ");
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == HomePage.NEW_HYDRATION_RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
      // TODO: Make this work
      // val hydrationRecord = new HydrationEntity(data.getStringExtra(NewHydrationRecordActivity.REPLY));
      // mainViewModel.insert(hydrationRecord);
    } else {
      Toast.makeText(getContext(), "Hydration record not saved!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    val leftEyeAnimation =
        new ImageViewAnimator(
            ANIMATION_DURATION, ANIMATION_START_OFFSET, leftEye, leftEyeAnimations);
    leftEyeAnimation.start();

    val rightEyeAnimation =
        new ImageViewAnimator(
            ANIMATION_DURATION, ANIMATION_START_OFFSET, rightEye, rightEyeAnimations);
    rightEyeAnimation.start();

    // TODO: Set runtime values in here
    // TODO: Maybe make the eyes stoppable on click?
  }
}
