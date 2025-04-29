package com.rdruzhchenko.fsjutils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class providing methods for generating random values.
 * This class contains methods for generating random integers, UUIDs, strings,
 * booleans, enums, and byte arrays.
 */
public class FSRandomUtils {

    private static final Random rand = new Random();

    /**
     * Generates a random integer between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value (inclusive)
     * @param max The maximum value (inclusive)
     * @return A random integer between min and max
     */
    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random integer between 0 and 20,000 (inclusive).
     *
     * @return A random integer between 0 and 20,000
     */
    public static int randInt() {
        return randInt(0, 20_000);
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
        Random rd = new Random();
        return rd.nextBoolean();
    }

    private static final Random PRNG = new Random();

    /**
     * Returns a random enum value from the specified enum class.
     *
     * @param <T> The enum type
     * @param clazz The enum class
     * @return A random enum value from the specified class
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        Random random = new Random();
        T[] enumValues = clazz.getEnumConstants();
        return enumValues[random.nextInt(enumValues.length)];
    }

    /**
     * Generates a random alphanumeric string with a random length between 1 and 50 characters.
     *
     * @return A random alphanumeric string
     */
    public static String randString() {
        int randomLength = rand.nextInt(50) + 1;

        return rand.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
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
        return FSRandomUtils.randString();
    }

    /**
     * Generates a random boolean value.
     * This is an alias for randBoolean().
     *
     * @return A random boolean value (true or false)
     */
    public static boolean randBool() {
        return rand.nextBoolean();
    }

    /**
     * Generates a random byte array with a length between 10 and 100.
     * Uses SecureRandom for cryptographically strong random bytes.
     *
     * @return A random byte array
     */
    public static byte[] randBytes() {
        byte[] randomBytes = new byte[randInt(10, 100)];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomBytes);
        return randomBytes;
    }
}
