package com.rdruzhchenko.fsjutils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class FSRandomUtils {

    private static Random rand = new Random();

    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int randInt() {
        return randInt(0, 20_000);
    }

    public static String randUuid() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    public static boolean randBoolean() {
        Random rd = new Random();
        return rd.nextBoolean();
    }

    private static final Random PRNG = new Random();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        Random random = new Random();
        T[] enumValues = clazz.getEnumConstants();
        return enumValues[random.nextInt(enumValues.length)];
    }

    public static String randString() {
        int randomLength = rand.nextInt(50) + 1;

        return rand.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(randomLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String randDate() {
        return FSRandomUtils.randString();
    }

    public static boolean randBool() {
        return rand.nextBoolean();
    }

    public static byte[] randBytes() {
        byte[] randomBytes = new byte[randInt(10, 100)]; // create a byte array of length 10
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomBytes);
        return randomBytes;
    }
}
