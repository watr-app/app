/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.utils;

import java.util.List;

/**
 * Miscellaneous array utilities and helpers.
 *
 * @author linuswillner
 * @version 1.0.0
 */
public class ArrayUtils {

  /**
   * Shorthand to (safely) get the last item in a List without needing to worry about
   * IndexOutOfBoundsException.
   *
   * @param list {@link List} List object to get the last item from
   * @return {@link Object} Last item of the list
   */
  public static Object last(List list) {
    return list.size() > 0 ? list.get(list.size() - 1) : null;
  }

  /**
   * Shorthand to get the index of the last item in a list, without going into the negatives.
   *
   * @param list {@link List} List object to get the last item index from
   * @return {@link Integer} Last item index
   */
  public static int lastIndex(List list) {
    return list.size() > 0 ? list.size() - 1 : 0;
  }
}
