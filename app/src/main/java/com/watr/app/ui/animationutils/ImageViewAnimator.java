/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.ui.animationutils;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Image view animator with support for sequencing based off of a list of TranslateAnimations.
 *
 * @see <a href="https://developer.android.com/reference/android/view/animation/TranslateAnimation">TranslateAnimation</a>
 * @author linuswillner
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class ImageViewAnimator {
  private final int duration;
  private final int startOffset;
  private final ImageView imageView;
  private final List<TranslateAnimation> animations;
  private int currentAnimation = 0;

  /**
   * Starts the animation sequence. Each animation in the provided constructor parameters is played
   * in sequence from first to last.
   */
  public void start() {
    val animation = animations.get(currentAnimation);
    currentAnimation = currentAnimation + 1 >= animations.size() ? 0 : currentAnimation + 1;

    // Initialise animation
    animation.setDuration(duration);
    animation.setStartOffset(startOffset);
    animation.setAnimationListener(new ImageViewAnimationListener(this));
    imageView.startAnimation(animation);
  }

  @RequiredArgsConstructor
  private static class ImageViewAnimationListener implements AnimationListener {
    private final ImageViewAnimator animator;

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
      animator.start();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
  }
}
