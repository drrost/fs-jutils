package com.rdruzhchenko.fsjutils.string;

/**
 * Utility class providing methods for name formatting and manipulation.
 * This class contains methods for creating full names, shortened names,
 * abbreviated names, and correcting name formatting.
 */
public class FSNameUtils {

    /**
     * Interface for objects that have first, last, and father's names.
     */
    public interface IFullNamed {
        /**
         * Gets the first name.
         *
         * @return The first name
         */
        String getFirstName();

        /**
         * Gets the last name.
         *
         * @return The last name
         */
        String getLastName();

        /**
         * Gets the father's name (patronymic).
         *
         * @return The father's name
         */
        String getFathersName();
    }

    /**
     * Creates a full name from an object implementing the IFullNamed interface.
     *
     * @param fullNamed The object containing the name components
     * @return The formatted full name in the format "LastName FirstName FathersName"
     */
    public static String fullName(IFullNamed fullNamed) {
        return fullName(fullNamed.getFirstName(), fullNamed.getFathersName(), fullNamed.getLastName());
    }

    /**
     * Creates a full name from individual name components.
     *
     * @param firstName The first name
     * @param fathersName The father's name (patronymic)
     * @param lastName The last name
     * @return The formatted full name in the format "LastName FirstName FathersName"
     */
    public static String fullName(String firstName, String fathersName, String lastName) {
        String result = "";
        if (lastName != null) {
            result += lastName;
        }
        if (firstName != null) {
            result += " " + firstName;
        }
        if (fathersName != null) {
            result += " " + fathersName;
        }
        return result.trim();
    }

    /**
     * Creates a short name format with capitalized first name and uppercase last name.
     *
     * @param firstName The first name
     * @param lastName The last name
     * @return The formatted short name in the format "FirstName LASTNAME"
     */
    public static String short_(String firstName, String lastName) {
        String result = "";
        if (firstName != null) {
            result += FSStringUtils.capitalize(firstName);
        }
        if (lastName != null) {
            result += " " + lastName.toUpperCase();
        }
        return result.trim();
    }

    /**
     * Creates the shortest name format with first initial and capitalized last name.
     *
     * @param firstName The first name
     * @param lastName The last name
     * @return The formatted shortest name in the format "F. Lastname"
     */
    public static String shortest(String firstName, String lastName) {
        String result = "";
        if (firstName != null) {
            result += firstName.substring(0, 1).toUpperCase();
        }
        if (lastName != null) {
            result += ". " + FSStringUtils.capitalize(lastName);
        }
        return result.trim();
    }

    /**
     * Creates the shortest name format from a full name string.
     * Assumes the full name is in the format "LastName FirstName [FathersName]".
     *
     * @param fullName The full name string
     * @return The formatted shortest name, or null if the input is null,
     *         or the original string if it contains only one word
     */
    public static String shortestFromFull(String fullName) {
        if (fullName == null) {
            return null;
        }

        var parts = fullName.split(" ");

        if (parts.length == 0) {
            return null;
        }

        if (parts.length == 1) {
            return parts[0];
        }

        return shortest(parts[1], parts[0]);
    }

    /**
     * Creates an abbreviated name format from a full name string.
     * Formats as "LastName F. [P.]" where F is the first letter of the first name
     * and P is the first letter of the patronymic (if present).
     *
     * @param fullName The full name string
     * @return The formatted abbreviated name, or null if the input is null,
     *         or the original string if it contains only one word
     */
    public static String abbreviatedFromFull(String fullName) {
        if (fullName == null) {
            return null;
        }

        var parts = fullName.split(" ");

        if (parts.length == 0) {
            return null;
        }

        if (parts.length == 1) {
            return parts[0];
        }

        String result = parts[0];
        result += " " + parts[1].charAt(0) + ".";

        if (parts.length > 2) {
            result += parts[2].charAt(0) + ".";
        }

        return result;
    }

    /**
     * Corrects the capitalization of a name according to specific rules.
     * Handles hyphenated names and special suffixes like "огли", "заде", and "кизи".
     * Also handles special prefixes like "да".
     *
     * @param name The name to correct
     * @return The name with corrected capitalization, or null if the input is null
     */
    public static String correct(String name) {
        if (name == null) {
            return null;
        }

        var parts = name.split("-");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            part = part.toLowerCase();
            if (!part.equals("огли") && !part.equals("заде") && !part.equals("кизи")) {
                parts[i] = FSStringUtils.capitalize(part);
            }
        }
        if (parts.length > 1) {
            return String.join("-", parts);
        }

        parts = name.split(" ");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            part = part.toLowerCase();
            if (i == 0 && part.equals("да")) {
                parts[i] = part;
            } else {
                parts[i] = FSStringUtils.capitalize(part);
            }
        }

        return String.join(" ", parts);
    }
}
