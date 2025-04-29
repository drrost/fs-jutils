package com.rdruzhchenko.fsjutils.dateutils;

import com.rdruzhchenko.fsjutils.date.FSDateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FSDateUtils")
class FSDateUtilsTest {

    @Nested
    @DisplayName("Date manipulation tests")
    class DateManipulationTests {

        @Test
        @DisplayName("Should subtract one day when adding negative days")
        void shouldSubtractOneDayWhenAddingNegativeDays() {
            // Given
            String date = "19.04.2022";

            // When
            String yesterday = FSDateUtils.dateByAddDays(date, -1);

            // Then
            assertEquals("18.04.2022", yesterday);
        }
    }

    @Nested
    @DisplayName("Date comparison tests")
    class DateComparisonTests {

        @Test
        @DisplayName("Should return false when first date has earlier day in same month and year")
        void shouldReturnFalseWhenFirstDateHasEarlierDayInSameMonthAndYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "20.06.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false when first date has earlier month in same year")
        void shouldReturnFalseWhenFirstDateHasEarlierMonthInSameYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "21.05.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false when first date has earlier year")
        void shouldReturnFalseWhenFirstDateHasEarlierYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "21.06.2021";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return true when first date has later day in same month and year")
        void shouldReturnTrueWhenFirstDateHasLaterDayInSameMonthAndYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "22.06.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return true when first date has later month in same year")
        void shouldReturnTrueWhenFirstDateHasLaterMonthInSameYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "21.07.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return true when first date has later year")
        void shouldReturnTrueWhenFirstDateHasLaterYear() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "21.06.2023";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when dates are equal")
        void shouldReturnFalseWhenDatesAreEqual() {
            // Given
            String date1 = "21.06.2022";
            String date2 = "21.06.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(date1, date2);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return true when comparing April to June dates")
        void shouldReturnTrueWhenComparingAprilToJuneDates() {
            // Given
            String dateOut = "29.04.2022";
            String dateX = "23.06.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(dateOut, dateX);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when comparing July to June dates")
        void shouldReturnFalseWhenComparingJulyToJuneDates() {
            // Given
            String dateOut = "01.07.2022";
            String dateX = "23.06.2022";

            // When
            boolean result = FSDateUtils.isDateLessThan(dateOut, dateX);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Week calculation tests")
    class WeekCalculationTests {

        @Nested
        @DisplayName("getWeekStart tests")
        class GetWeekStartTests {

            @Test
            @DisplayName("Should return correct week start for date in previous week")
            void shouldReturnCorrectWeekStartForDateInPreviousWeek() {
                // Given
                String date = "26.02.2022";

                // When
                var weekStart = FSDateUtils.getWeekStart(date);

                // Then
                assertEquals("21.02.2022", weekStart);
            }

            @Test
            @DisplayName("Should return same date when date is first day of week")
            void shouldReturnSameDateWhenDateIsFirstDayOfWeek() {
                // Given
                String date = "28.02.2022";

                // When
                var weekStart = FSDateUtils.getWeekStart(date);

                // Then
                assertEquals("28.02.2022", weekStart);
            }

            @Test
            @DisplayName("Should return correct week start for first day of month")
            void shouldReturnCorrectWeekStartForFirstDayOfMonth() {
                // Given
                String date = "01.03.2022";

                // When
                var weekStart = FSDateUtils.getWeekStart(date);

                // Then
                assertEquals("28.02.2022", weekStart);
            }

            @Test
            @DisplayName("Should return correct week start for middle of week")
            void shouldReturnCorrectWeekStartForMiddleOfWeek() {
                // Given
                String date = "04.03.2022";

                // When
                var weekStart = FSDateUtils.getWeekStart(date);

                // Then
                assertEquals("28.02.2022", weekStart);
            }

            @Test
            @DisplayName("Should return correct week start for last day of week")
            void shouldReturnCorrectWeekStartForLastDayOfWeek() {
                // Given
                String date = "06.03.2022";

                // When
                var weekStart = FSDateUtils.getWeekStart(date);

                // Then
                assertEquals("28.02.2022", weekStart);
            }
        }

        @Nested
        @DisplayName("getWeekEnd tests")
        class GetWeekEndTests {

            @Test
            @DisplayName("Should return correct week end for first day of week")
            void shouldReturnCorrectWeekEndForFirstDayOfWeek() {
                // Given
                var date = "28.02.2022";

                // When
                var weekEnd = FSDateUtils.getWeekEnd(date);

                // Then
                assertEquals("06.03.2022", weekEnd);
            }

            @Test
            @DisplayName("Should return same date when date is last day of week")
            void shouldReturnSameDateWhenDateIsLastDayOfWeek() {
                // Given
                var date = "06.03.2022";

                // When
                var weekEnd = FSDateUtils.getWeekEnd(date);

                // Then
                assertEquals("06.03.2022", weekEnd);
            }

            @Test
            @DisplayName("Should return correct week end for middle of week")
            void shouldReturnCorrectWeekEndForMiddleOfWeek() {
                // Given
                var date = "01.03.2022";

                // When
                var weekEnd = FSDateUtils.getWeekEnd(date);

                // Then
                assertEquals("06.03.2022", weekEnd);
            }

            @Test
            @DisplayName("Should return same date when date is in previous week")
            void shouldReturnSameDateWhenDateIsInPreviousWeek() {
                // Given
                var date = "27.02.2022";

                // When
                var weekEnd = FSDateUtils.getWeekEnd(date);

                // Then
                assertEquals("27.02.2022", weekEnd);
            }
        }
    }

    @Nested
    @DisplayName("Time manipulation and comparison tests")
    class TimeTests {

        @Nested
        @DisplayName("Time extraction and manipulation tests")
        class TimeExtractionAndManipulationTests {

            @Test
            @DisplayName("Should extract time from date-time string")
            void shouldExtractTimeFromDateTimeString() {
                // Given
                var date = "27.02.2022 23:20";

                // When
                var time = FSDateUtils.getTimeFromDateTime(date);

                // Then
                assertEquals("23:20", time);
            }

            @Test
            @DisplayName("Should add minutes to time correctly")
            void shouldAddMinutesToTimeCorrectly() {
                // Given
                var date = "23:20";

                // When
                var time = FSDateUtils.plusMinutesToTime(date, 5);

                // Then
                assertEquals("23:25", time);
            }
        }

        @Nested
        @DisplayName("Time comparison tests")
        class TimeComparisonTests {

            @Nested
            @DisplayName("isTimeLessThan tests")
            class IsTimeLessThanTests {

                @Test
                @DisplayName("Should return true when first time is earlier than second time")
                void shouldReturnTrueWhenFirstTimeIsEarlierThanSecondTime() {
                    // Given
                    String time1 = "15:00";
                    String time2 = "15:15";

                    // When
                    boolean result = FSDateUtils.isTimeLessThan(time1, time2);

                    // Then
                    assertTrue(result);
                }

                @Test
                @DisplayName("Should return false when first time is later than second time")
                void shouldReturnFalseWhenFirstTimeIsLaterThanSecondTime() {
                    // Given
                    String time1 = "23:37";
                    String time2 = "15:15";

                    // When
                    boolean result = FSDateUtils.isTimeLessThan(time1, time2);

                    // Then
                    assertFalse(result);
                }

                @Test
                @DisplayName("Should return false when times are equal")
                void shouldReturnFalseWhenTimesAreEqual() {
                    // Given
                    String time = "23:37";

                    // When
                    boolean result = FSDateUtils.isTimeLessThan(time, time);

                    // Then
                    assertFalse(result);
                }
            }

            @Nested
            @DisplayName("isTimeGreaterThan tests")
            class IsTimeGreaterThanTests {

                @Test
                @DisplayName("Should return false when first time is earlier than second time")
                void shouldReturnFalseWhenFirstTimeIsEarlierThanSecondTime() {
                    // Given
                    String time1 = "15:00";
                    String time2 = "15:15";

                    // When
                    boolean result = FSDateUtils.isTimeGreaterThan(time1, time2);

                    // Then
                    assertFalse(result);
                }

                @Test
                @DisplayName("Should return true when first time is later than second time")
                void shouldReturnTrueWhenFirstTimeIsLaterThanSecondTime() {
                    // Given
                    String time1 = "23:37";
                    String time2 = "15:15";

                    // When
                    boolean result = FSDateUtils.isTimeGreaterThan(time1, time2);

                    // Then
                    assertTrue(result);
                }

                @Test
                @DisplayName("Should return false when times are equal")
                void shouldReturnFalseWhenTimesAreEqual() {
                    // Given
                    String time = "23:37";

                    // When
                    boolean result = FSDateUtils.isTimeGreaterThan(time, time);

                    // Then
                    assertFalse(result);
                }

                @Test
                @DisplayName("Should return false when times are equal but created differently")
                void shouldReturnFalseWhenTimesAreEqualButCreatedDifferently() {
                    // Given
                    String time1 = "23:37";
                    String time2 = "23:3";
                    time2 += "7";

                    // When
                    boolean result = FSDateUtils.isTimeGreaterThan(time1, time2);

                    // Then
                    assertFalse(result);
                }
            }
        }
    }

    @Nested
    @DisplayName("Date comparison with time tests")
    class DateComparisonWithTimeTests {

        @Test
        @DisplayName("Should return zero when dates with time are equal")
        void shouldReturnZeroWhenDatesWithTimeAreEqual() {
            // Given
            String time1 = "27.04.2024 14:46:29";
            String time2 = "27.04.2024 14:46:29";

            // When
            int result = FSDateUtils.compareDates(time1, time2);

            // Then
            assertEquals(0, result);
        }

        @Test
        @DisplayName("Should return negative one when first date is earlier than second date")
        void shouldReturnNegativeOneWhenFirstDateIsEarlierThanSecondDate() {
            // Given
            String time1 = "27.04.2024 14:46:29";
            String time2 = "27.04.2024 14:46:30";

            // When
            int result = FSDateUtils.compareDates(time1, time2);

            // Then
            assertEquals(-1, result);
        }

        @Test
        @DisplayName("Should return positive one when first date is later than second date")
        void shouldReturnPositiveOneWhenFirstDateIsLaterThanSecondDate() {
            // Given
            String time1 = "27.04.2024 14:46:30";
            String time2 = "27.04.2024 14:46:29";

            // When
            int result = FSDateUtils.compareDates(time1, time2);

            // Then
            assertEquals(1, result);
        }
    }
}
