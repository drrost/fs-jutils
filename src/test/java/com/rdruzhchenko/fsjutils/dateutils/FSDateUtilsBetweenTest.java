package com.rdruzhchenko.fsjutils.dateutils;

import com.rdruzhchenko.fsjutils.FSDateUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FSDateUtilsBetweenTest {

    @Test
    public void testToday() {
        // Given
        var today = FSDateUtils.getCurrentDateAsDate();

        // When
        var days = FSDateUtils.daysBetween(today, today);

        // Then
        assertEquals(0, days);
    }

    @Test
    public void testOneDay() {
        // Given
        var today = FSDateUtils.getCurrentDateAsDate();
        var yesterday = FSDateUtils.dateByAddDaysAsDate(today, -1);

        // When
        var days = FSDateUtils.daysBetween(yesterday, today);

        // Then
        assertEquals(1, days);
    }

    @Test
    public void test30DaysApril() {
        // Given
        var dateInString = "12.04.2022";
        var dateOutString = "12.05.2022";
        var dateIn = FSDateUtils.dateFromString(dateInString);
        var dateOut = FSDateUtils.dateFromString(dateOutString);

        // When
        var days = FSDateUtils.daysBetween(dateIn, dateOut);

        // Then
        assertEquals(30, days);
    }

    @Test
    public void test30DaysMay() {
        // Given
        var dateInString = "12.05.2022";
        var dateOutString = "12.06.2022";
        var dateIn = FSDateUtils.dateFromString(dateInString);
        var dateOut = FSDateUtils.dateFromString(dateOutString);

        // When
        var days = FSDateUtils.daysBetween(dateIn, dateOut);

        // Then
        assertEquals(31, days);
    }
}