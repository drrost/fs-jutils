package com.rdruzhchenko.fsjutils;

public class FSPhoneNumberFormatter {

    public static String format(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
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
