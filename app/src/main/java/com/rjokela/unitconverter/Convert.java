package com.rjokela.unitconverter;

/**
 * Created by Randon K. Jokela on 7/9/2015.
 * Various unit conversion functions.
 */
public class Convert {
    private static final double[] CONVERSION_FACTOR = { 2.2, 1.6, 1.8 };

    public static double kilogramsToPounds(double kg) {
        return kg * CONVERSION_FACTOR[0];
    }

    public static double poundsToKilograms(double lbs) {
        return lbs / CONVERSION_FACTOR[0];
    }

    public static double milesToKilometers(double miles) {
        return miles * CONVERSION_FACTOR[1];
    }

    public static double kilometersToMiles(double km) {
        return km / CONVERSION_FACTOR[1];
    }

    public static double fahrenheitToCelsius(double F) {
        return (F - 32.0) / CONVERSION_FACTOR[2];
    }

    public static double celsiusToFahrenheit(double C) {
        return C * CONVERSION_FACTOR[2] + 32.0;
    }
}
