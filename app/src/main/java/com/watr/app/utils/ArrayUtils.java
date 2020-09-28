/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.utils;

import java.util.List;

/**
 * Miscellaneous array utilities and helpers.
 */
public class ArrayUtils {

  /**
   * Shorthand to (safely) get the last item in a List without needing to worry about
   * IndexOutOfBoundsException.
   *
   * @param list List object to get the last item from
   * @return Last item of the list
   */
  public static Object last(List list) {
    return list.size() > 0 ? list.get(list.size() - 1) : null;
  }

  /**
   * Shorthand to get the index of the last item in a list, without going into the negatives.
   * @param list List object to get the last item index from
   * @return Last item index
   */
  public static int lastIndex(List list) {
    return list.size() > 0 ? list.size() - 1 : 0;
  }
}
