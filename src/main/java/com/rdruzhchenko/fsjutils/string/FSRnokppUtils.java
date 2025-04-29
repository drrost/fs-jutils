package com.rdruzhchenko.fsjutils.string;

import com.rdruzhchenko.fsjutils.date.FSDateUtils;
import com.rdruzhchenko.fsjutils.exception.FSValidationException;
import java.time.LocalDate;

/**
 * Utility class for working with RNOKPP codes (Ukrainian tax identification numbers).
 * This class provides methods for validating RNOKPP codes and extracting information
 * such as date of birth and gender from them.
 */
public class FSRnokppUtils {

    /**
     * Validates an RNOKPP code using the control digit algorithm.
     *
     * @param code The RNOKPP code to validate
     * @return true if the code is valid, false otherwise
     */
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

    /**
     * Extracts the date of birth from an RNOKPP code.
     * The first 5 digits of the RNOKPP code represent the number of days since December 31, 1899.
     *
     * @param code The RNOKPP code
     * @return The date of birth in the format dd.MM.yyyy
     * @throws FSValidationException if the code is null or not exactly 10 digits
     */
    public static String getDob(String code) {
        if (code == null || !code.matches("\\d{10}")) {
            throw new FSValidationException("Invalid RNOKPP code format: " + code + ". RNOKPP code must contain exactly 10 digits.");
        }

        int daysSinceBase = Integer.parseInt(code.substring(0, 5));
        LocalDate baseDate = LocalDate.of(1899, 12, 31);
        var date = baseDate.plusDays(daysSinceBase);
        return FSDateUtils.dateToString(FSDateUtils.localDateToDate(date));
    }

    /**
     * Gender enumeration for use with RNOKPP codes.
     */
    public enum Gender {
        /** Male gender */
        MALE, 
        /** Female gender */
        FEMALE
    }

    /**
     * Extracts the gender from an RNOKPP code.
     * The 9th digit of the RNOKPP code indicates gender:
     * odd numbers for males, even numbers for females.
     *
     * @param code The RNOKPP code
     * @return The gender (MALE or FEMALE)
     * @throws FSValidationException if the code is null or not exactly 10 digits
     */
    public static Gender getGender(String code) {
        if (code == null || !code.matches("\\d{10}")) {
            throw new FSValidationException("Invalid RNOKPP code format: " + code + ". RNOKPP code must contain exactly 10 digits.");
        }

        int genderDigit = Character.getNumericValue(code.charAt(8));
        return (genderDigit % 2 == 0) ? Gender.FEMALE : Gender.MALE;
    }
}
