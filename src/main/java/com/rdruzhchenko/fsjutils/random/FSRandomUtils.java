package com.rdruzhchenko.fsjutils.random;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class providing methods for generating random values.
 * This class contains methods for generating random integers, UUIDs, strings,
 * booleans, enums, and byte arrays.
 */
public class FSRandomUtils {

    private static final Random RAND = new Random();

    // Constants for magic numbers
    private static final int DEFAULT_MAX_INT = 20_000;
    private static final int DEFAULT_MIN_INT = 0;
    private static final int MAX_STRING_LENGTH = 50;
    private static final int MIN_STRING_LENGTH = 1;
    private static final int MIN_BYTES_LENGTH = 10;
    private static final int MAX_BYTES_LENGTH = 100;

    // ASCII character ranges
    private static final int ASCII_RANGE_START = 48;  // '0'
    private static final int ASCII_RANGE_END = 123;   // 'z' + 1
    private static final int ASCII_DIGIT_END = 57;    // '9'
    private static final int ASCII_UPPER_START = 65;  // 'A'
    private static final int ASCII_UPPER_END = 90;    // 'Z'
    private static final int ASCII_LOWER_START = 97;  // 'a'

    /**
     * Generates a random integer between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value (inclusive)
     * @param max The maximum value (inclusive)
     * @return A random integer between min and max
     */
    public static int randInt(int min, int max) {
        return RAND.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random integer between 0 and 20,000 (inclusive).
     *
     * @return A random integer between 0 and 20,000
     */
    public static int randInt() {
        return randInt(DEFAULT_MIN_INT, DEFAULT_MAX_INT);
    }

    /**
     * Generates a random UUID as a lowercase string.
     *
     * @return A random UUID string
     */
    public static String randUuid() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    /**
     * Generates a random boolean value.
     *
     * @return A random boolean value (true or false)
     */
    public static boolean randBoolean() {
        return RAND.nextBoolean();
    }

    /**
     * Returns a random enum value from the specified enum class.
     *
     * @param <T> The enum type
     * @param clazz The enum class
     * @return A random enum value from the specified class
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] enumValues = clazz.getEnumConstants();
        return enumValues[RAND.nextInt(enumValues.length)];
    }

    /**
     * Generates a random alphanumeric string with a random length between 1 and 50 characters.
     *
     * @return A random alphanumeric string
     */
    public static String randString() {
        int randomLength = RAND.nextInt(MAX_STRING_LENGTH) + MIN_STRING_LENGTH;

        return RAND.ints(ASCII_RANGE_START, ASCII_RANGE_END)
                .filter(i -> (i <= ASCII_DIGIT_END || i >= ASCII_UPPER_START) && 
                             (i <= ASCII_UPPER_END || i >= ASCII_LOWER_START))
                .limit(randomLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Generates a random string that could be used as a date.
     * Note: This does not actually generate a valid date format, but uses randString().
     *
     * @return A random string
     */
    public static String randDate() {
        return randString();
    }

    /**
     * Generates a random byte array with a length between 10 and 100.
     * Uses SecureRandom for cryptographically strong random bytes.
     *
     * @return A random byte array
     */
    public static byte[] randBytes() {
        byte[] randomBytes = new byte[randInt(MIN_BYTES_LENGTH, MAX_BYTES_LENGTH)];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }
}
