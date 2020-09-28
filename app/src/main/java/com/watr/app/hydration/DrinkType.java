/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * All rights reserved.
 */

package com.watr.app.hydration;

import lombok.AllArgsConstructor;

/** Drink types used in the application, with their labels and hydration coefficients. */
@AllArgsConstructor
public enum DrinkType {
  WATER("Water", 1.0),
  WATER_CARBONATED("Water (Carbonated)", 0.85),
  WATER_FLAVOURED("Water (Flavoured)", 0.75),
  MILK("Milk", 0.8),
  TEA("Tea", 0.8),
  COFFEE("Coffee", 0.7),
  JUICE("Juice", 0.6),
  HOT_CHOCOLATE("Hot chocolate", 0.5),
  SOFT_DRINK("Soft drink", 0.4),
  ENERGY_DRINK("Energy drink", 0.4),
  BEER("Beer", -1.0),
  WINE("Wine", -1.0),
  CHAMPAGNE("Champagne", -2.0),
  SPIRIT("Spirit", -4.0);

  public final String label;
  public final double hydrationCoefficient;
}
