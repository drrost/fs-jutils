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

    private static final int RNOKPP_LENGTH = 10;
    private static final String RNOKPP_PATTERN = "\\d{" + RNOKPP_LENGTH + "}";
    private static final String INVALID_FORMAT_MESSAGE = "Invalid RNOKPP code format: %s. RNOKPP code must contain exactly " + RNOKPP_LENGTH + " digits.";

    // Constants for control digit calculation
    private static final int[] CONTROL_WEIGHTS = {-1, 5, 7, 9, 4, 6, 10, 5, 7};
    private static final int MODULO_11 = 11;
    private static final int MODULO_10 = 10;

    // Constants for date extraction
    private static final int DATE_DIGITS_LENGTH = 5;
    private static final int BASE_YEAR = 1899;
    private static final int BASE_MONTH = 12;
    private static final int BASE_DAY = 31;

    // Constants for gender extraction
    private static final int GENDER_DIGIT_INDEX = 8;

    /**
     * Validates an RNOKPP code using the control digit algorithm.
     *
     * @param code The RNOKPP code to validate
     * @return true if the code is valid, false otherwise
     */
    public static boolean isValidRnokpp(String code) {
        if (!isValidRnokppFormat(code)) {
            return false;
        }

        int[] digits = new int[RNOKPP_LENGTH];
        for (int i = 0; i < RNOKPP_LENGTH; i++) {
            digits[i] = Character.getNumericValue(code.charAt(i));
        }

        int controlSum = 0;
        for (int i = 0; i < RNOKPP_LENGTH - 1; i++) {
            controlSum += digits[i] * CONTROL_WEIGHTS[i];
        }

        int controlDigit = (controlSum % MODULO_11) % MODULO_10;

        return controlDigit == digits[RNOKPP_LENGTH - 1];
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
        validateRnokppFormat(code);

        int daysSinceBase = Integer.parseInt(code.substring(0, DATE_DIGITS_LENGTH));
        LocalDate baseDate = LocalDate.of(BASE_YEAR, BASE_MONTH, BASE_DAY);
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
        validateRnokppFormat(code);

        int genderDigit = Character.getNumericValue(code.charAt(GENDER_DIGIT_INDEX));
        return (genderDigit % 2 == 0) ? Gender.FEMALE : Gender.MALE;
    }

    /**
     * Checks if the RNOKPP code has a valid format (not null and exactly 10 digits).
     * 
     * @param code The RNOKPP code to check
     * @return true if the code has a valid format, false otherwise
     */
    private static boolean isValidRnokppFormat(String code) {
        return code != null && code.matches(RNOKPP_PATTERN);
    }

    /**
     * Validates the RNOKPP code format and throws an exception if invalid.
     * 
     * @param code The RNOKPP code to validate
     * @throws FSValidationException if the code is null or not exactly 10 digits
     */
    private static void validateRnokppFormat(String code) {
        if (!isValidRnokppFormat(code)) {
            throw new FSValidationException(String.format(INVALID_FORMAT_MESSAGE, code));
        }
    }
}
