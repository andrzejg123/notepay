package pl.polsl.notepay.util;

public class DoubleUtil {

    private static final double EPSILON = 0.000001d;

    public static boolean equals(Double a, Double b) {
        return Math.abs(a - b) < EPSILON;
    }

}
