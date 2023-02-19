package com.library.utilities;

public class NumberUtils {

    private NumberUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static int convertToInt(String intString, int defValue) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return defValue;
    }

    public static long convertToLong(String longString, long defValue) {
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return defValue;
    }

    public static float convertToFloat(String floatString, float defValue) {
        try {
            return Float.parseFloat(floatString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return defValue;
    }

    public static double convertToDouble(String doubleString, double defValue) {
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longString) {
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String floatString) {
        try {
            return Float.parseFloat(floatString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String doubleString) {
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return null;
    }
}
