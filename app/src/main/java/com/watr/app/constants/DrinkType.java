/*
 * Watr. Android application
 * Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.
 * Licensed under the MIT license.
 */

package com.watr.app.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Constants for drink types used in the application, with their labels and hydration coefficients.
 *
 * @author linuswillner
 * @version 1.0.0
 */
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

  @Getter private final String label;
  @Getter private final double hydrationCoefficient;
}
