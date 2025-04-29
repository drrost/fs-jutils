package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.exception.FSValidationException;
import java.time.LocalDate;

public class FSRnokppUtils {

    public static boolean isValidRnokpp(String code) {
        if (code == null || !code.matches("\\d{10}")) {
            return false;
        }

        int[] digits = new int[10];
        for (int i = 0; i < 10; i++) {
            digits[i] = Character.getNumericValue(code.charAt(i));
        }

        int[] weights = {-1, 5, 7, 9, 4, 6, 10, 5, 7};

        int controlSum = 0;
        for (int i = 0; i < 9; i++) {
            controlSum += digits[i] * weights[i];
        }

        int controlDigit = (controlSum % 11) % 10;

        return controlDigit == digits[9];
    }

    public static String getDob(String code) {
        if (code == null || !code.matches("\\d{10}")) {
            throw new FSValidationException("Invalid RNOKPP code format: " + code + ". RNOKPP code must contain exactly 10 digits.");
        }

        int daysSinceBase = Integer.parseInt(code.substring(0, 5));
        LocalDate baseDate = LocalDate.of(1899, 12, 31);
        var date = baseDate.plusDays(daysSinceBase);
        return FSDateUtils.dateToString(FSDateUtils.localDateToDate(date));
    }

    public enum Gender {
        MALE, FEMALE
    }

    public static Gender getGender(String code) {
        if (code == null || !code.matches("\\d{10}")) {
            throw new FSValidationException("Invalid RNOKPP code format: " + code + ". RNOKPP code must contain exactly 10 digits.");
        }

        int genderDigit = Character.getNumericValue(code.charAt(8));
        return (genderDigit % 2 == 0) ? Gender.FEMALE : Gender.MALE;
    }
}
