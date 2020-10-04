/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.ui.utils;

import android.util.Log;
import com.watr.app.utils.ArrayUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.val;

/**
 * Utility class to keep track of what page is currently selected in the view pager.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class PageTracker {
  private final List<Integer> previousPages = new ArrayList<>();

  @Getter private int currentPage;

  private boolean ignoreNextSet;

  public PageTracker(int initialPage) {
    this.currentPage = initialPage;
  }

  /**
   * Updates the current page index and stores the now old page index in history.
   *
   * @param newPage {@link Integer} Index of the new page
   */
  public void setCurrentPage(int newPage) {
    if (!shouldIgnoreNext()) {
      previousPages.add(currentPage);
    } else {
      Log.d(
          "page-tracker",
          "setCurrentPage() triggered in wake of backpress, omitting this walkback change from history");
    }

    currentPage = newPage;

    Log.d("page-tracker", String.format("Setting current page to %d", newPage));
  }

  /**
   * Retrieves the previous page and removes that page from the history.
   *
   * @return {@link Integer} Previous page index
   */
  public int getPreviousPage() {
    Log.d(
        "page-tracker",
        String.format("Getting previous page from history (%s)", previousPages.toString()));

    val previous = (int) ArrayUtils.last(previousPages);
    previousPages.remove(ArrayUtils.lastIndex(previousPages));

    Log.d("page-tracker", String.format("Setting previous page to %d", previous));

    return previous;
  }

  /**
   * Checks if the current page is the first instance of any page in the application, while checking
   * for a backpress and, if so, notifying that the next setCurrentPage() call needs to be omitted
   * from history (Seeing as setCurrentPage always triggers). The latter is utilised to allow the
   * history to actually run out at some point, instead of just flipping between the same two items
   * all the time.
   *
   * @param isBackPress {@link Boolean} Whether this function was triggered from a backpress, i.e. pressing Back
   * @return {@link Boolean} Whether this page is the first instance (Or not)
   */
  public boolean currentPageIsFirst(boolean isBackPress) {
    if (isBackPress) {
      Log.d(
          "page-tracker",
          "currentPageIsFirst() triggered by backpress, ignoring next setCurrentPage()");
      ignoreNextSet = true;
    }

    val isPrevious = previousPages.size() == 0;
    Log.d("page-tracker", String.format("currentPageIsFirst(): %s", isPrevious));
    return isPrevious;
  }

  /**
   * Pre-flight check for whether the upcoming setCurrentPage() call should be omitted from history.
   * If so, returns true and then flips the ignore switch back to false, in order to allow for the
   * check to occur again from a clean slate on the next setCurrentPage() call.
   *
   * @return {@link Boolean} Whether the next setCurrentPage() call should be ignored (Or not)
   */
  private boolean shouldIgnoreNext() {
    // If next change should be ignored, return true for ignore and reset tracker
    if (ignoreNextSet) {
      ignoreNextSet = false;
      return true;
    } else {
      return false;
    }
  }
}
