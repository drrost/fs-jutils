package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.exception.FSStringException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class providing various string manipulation and validation operations.
 * This class contains methods for trimming, capitalization, character manipulation,
 * path handling, and string validation.
 */
public class FSStringUtils {
    /**
     * Trims all whitespace from a string and replaces multiple spaces with a single space.
     *
     * @param s The string to trim
     * @return The trimmed string, or null if the input is null
     */
    public static String trimAll(String s) {
        if (s == null) {
            return null;
        }
        return s.trim().replaceAll("\\s+", " ");
    }

    /**
     * Removes leading whitespace from a string.
     *
     * @param s The string to trim
     * @return The string with leading whitespace removed, or null if the input is null
     */
    public static String trimLeading(String s) {
        if (s == null) {
            return null;
        }
        return s.replaceFirst("^\\s+", "");
    }

    /**
     * Trims all whitespace and removes leading colons from a string.
     *
     * @param s The string to process
     * @return The processed string with whitespace trimmed and leading colons removed
     */
    public static String trimAllAndColon(String s) {
        return trimAll(trimLeadingColon(s));
    }

    /**
     * Removes leading colons from a string.
     *
     * @param s The string to process
     * @return The string with leading colons removed, or null if the input is null
     */
    public static String trimLeadingColon(String s) {
        if (s == null) {
            return null;
        }
        return s.replaceAll("^:", "");
    }

    /**
     * Checks if a string can be parsed as an integer.
     *
     * @param s The string to check
     * @return true if the string can be parsed as an integer, false otherwise
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Capitalizes the first character of a string.
     *
     * @param s The string to capitalize
     * @return The capitalized string, or the original string if it's null or empty
     */
    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Converts the first character of a string to lowercase.
     *
     * @param s The string to decapitalize
     * @return The decapitalized string, or the original string if it's null or empty
     */
    public static String decapitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * Returns the character at the specified index as a string.
     *
     * @param s The source string
     * @param i The index of the character to return
     * @return The character at the specified index as a string
     */
    public static String charAt(String s, int i) {
        char ch = s.charAt(i);
        return ch + "";
    }

    /**
     * Truncates a string to the specified length if it exceeds that length.
     *
     * @param s The string to truncate
     * @param length The maximum length of the string
     * @return The truncated string, or the original string if it's shorter than the specified length
     */
    public static String trunkTillLength(String s, int length) {
        if (s.length() <= length) {
            return s;
        } else {
            return s.substring(0, length);
        }
    }

    /**
     * Returns the last character of a string as a string.
     *
     * @param unit The string to get the last character from
     * @return The last character as a string, or null if the input is null
     */
    public static String getLast(String unit) {
        if (unit != null) {
            return unit.substring(unit.length() - 1, unit.length());
        }
        return null;
    }

    /**
     * Returns the last character of a string as a Character.
     *
     * @param s The string to get the last character from
     * @return The last character as a Character
     * @throws FSStringException if the input string is null
     */
    public static Character getLastChar(String s) {
        if (s == null) {
            throw new FSStringException("Cannot get last character: input string is null");
        }

        var lastString = getLast(s);
        return lastString.charAt(0);
    }

    /**
     * Checks if a string is null or empty (after trimming).
     *
     * @param s The string to check
     * @return true if the string is null or empty after trimming, false otherwise
     */
    public static boolean nullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        return s.trim().equals("");
    }

    /**
     * Removes the first word from a string.
     *
     * @param s The string to process
     * @return The string with the first word removed, or an empty string if there's only one word
     */
    public static String dropFirstWord(String s) {
        s = FSStringUtils.trimLeading(s);

        int index = s.indexOf(" ");
        if (index > -1) {
            return s.substring(index + 1);
        }

        return "";
    }

    /**
     * Removes the last component from a path string, keeping the trailing slash.
     *
     * @param s The path string to process
     * @return The path with the last component removed, or an empty string if there's no path separator
     * @throws FSStringException if the input path is null
     */
    public static String dropLastPathComponent(String s) {
        if (s == null) {
            throw new FSStringException("Cannot drop last path component: input path is null");
        }

        int lastIndex = s.lastIndexOf("/");
        if (lastIndex == -1) {
            return "";
        }

        return s.substring(0, lastIndex + 1);
    }

    /**
     * Removes all newline characters from a string.
     *
     * @param s The string to process
     * @return The string with all newline characters removed
     */
    public String removeNewlines(String s) {
        s = s.replace("\r\n", "");
        return s.replace("\n", "");
    }

    /**
     * Splits a full filename into name and extension.
     *
     * @param fileName Full file name
     * @return An array with [name, extension]. If no extension is found,
     * extension will be an empty string.
     */
    public static String[] splitFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex + 1);
            return new String[]{name, extension};
        }
        return new String[]{fileName, ""};
    }

    /**
     * Returns the last component of a path string.
     *
     * @param s The path string to process
     * @return The last component of the path, or null if the input is null,
     *         or the original string if it's empty or just "/"
     */
    public static String lastPathComponent(String s) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty()) {
            return s;
        }
        if (s.equals("/")) {
            return s;
        }

        Path p = Paths.get(s);
        Path fileName = p.getFileName();
        return fileName == null ? "" : fileName.toString();
    }

    /**
     * Checks if a string contains only uppercase letters.
     * Non-letter characters are ignored in this check.
     *
     * @param input The string to check
     * @return true if the string contains only uppercase letters (ignoring non-letter characters),
     *         false if the string is null, empty, or contains any lowercase letters
     */
    public static boolean isAllUpperCase(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) && !Character.isUpperCase(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a string is properly capitalized (first letter uppercase, rest lowercase).
     *
     * @param input The string to check
     * @return true if the string is properly capitalized,
     *         false if the string is null, empty, or not properly capitalized
     */
    public static boolean isCapitalized(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        char firstChar = input.charAt(0);
        if (!Character.isUpperCase(firstChar)) {
            return false;
        }

        for (int i = 1; i < input.length(); i++) {
            if (!Character.isLowerCase(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
