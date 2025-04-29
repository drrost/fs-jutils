package com.rdruzhchenko.fsjutils.dateutils;

import com.rdruzhchenko.fsjutils.FSDateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FSDateUtils - daysBetween method")
class FSDateUtilsBetweenTest {

    @Nested
    @DisplayName("daysBetween tests")
    class DaysBetweenTests {

        @Test
        @DisplayName("Should return zero days when dates are the same")
        public void shouldReturnZeroDaysWhenDatesAreTheSame() {
            // Given
            var today = FSDateUtils.getCurrentDateAsDate();

            // When
            var days = FSDateUtils.daysBetween(today, today);

            // Then
            assertEquals(0, days);
        }

        @Test
        @DisplayName("Should return one day when comparing today with yesterday")
        public void shouldReturnOneDayWhenComparingTodayWithYesterday() {
            // Given
            var today = FSDateUtils.getCurrentDateAsDate();
            var yesterday = FSDateUtils.dateByAddDaysAsDate(today, -1);

            // When
            var days = FSDateUtils.daysBetween(yesterday, today);

            // Then
            assertEquals(1, days);
        }

        @Test
        @DisplayName("Should return 30 days for April to May period")
        public void shouldReturn30DaysForAprilToMayPeriod() {
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
        @DisplayName("Should return 31 days for May to June period")
        public void shouldReturn31DaysForMayToJunePeriod() {
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
}
