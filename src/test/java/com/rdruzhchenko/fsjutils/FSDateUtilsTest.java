package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FSDateUtils")
class FSDateUtilsTest {

    @Nested
    @DisplayName("Date validation tests")
    class DateValidationTests {

        @Test
        @DisplayName("Should return false when input is not a date")
        void shouldReturnFalseWhenInputIsNotADate() {
            // Given
            var date = "no date";

            // When
            var result = FSDateUtils.isValidDate(date);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false when input contains extra characters")
        void shouldReturnFalseWhenInputContainsExtraCharacters() {
            // Given
            var date = "22.06.2022,";

            // When
            var result = FSDateUtils.isValidDate(date);

            // Then
            assertFalse(result);
        }
    }
}
