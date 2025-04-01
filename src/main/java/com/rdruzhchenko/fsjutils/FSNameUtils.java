package com.rdruzhchenko.fsjutils;

public class FSNameUtils {

    public interface IFullNamed {
        String getFirstName();
        String getLastName();
        String getFathersName();
    }

    public static String fullName(IFullNamed fullNamed) {
        return fullName(fullNamed.getFirstName(), fullNamed.getFathersName(), fullNamed.getLastName());
    }

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
