package com.rdruzhchenko.fsjutils.string;

/**
 * Utility class for formatting phone numbers.
 * This class provides methods to standardize phone number formats,
 * particularly for Ukrainian phone numbers.
 */
public class FSPhoneNumberFormatter {

    /**
     * Formats a phone number by removing non-digit characters and adding
     * the Ukrainian country code (+38) if needed.
     *
     * @param phoneNumber The phone number to format
     * @return The formatted phone number, or the original input if it cannot be formatted
     *         or if the input is null or blank
     */
    public static String format(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return phoneNumber;
        }

        String digits = phoneNumber.replaceAll("[^\\d+]", "");

        if (!digits.startsWith("+")) {
            if (digits.length() == 10) {
                digits = "+38" + digits;
            } else {
                return phoneNumber;
            }
        }

        return digits;
    }
}
