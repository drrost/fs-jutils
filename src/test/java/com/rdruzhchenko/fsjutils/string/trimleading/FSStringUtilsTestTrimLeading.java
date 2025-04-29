package com.rdruzhchenko.fsjutils.string.trimleading;

import com.rdruzhchenko.fsjutils.string.FSStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FSStringUtils - trimLeading method")
public class FSStringUtilsTestTrimLeading {

    @Nested
    @DisplayName("trimLeading tests")
    class TrimLeadingTests {

        @Test
        @DisplayName("Should return empty string when input is empty")
        void shouldReturnEmptyStringWhenInputIsEmpty() {
            // Given
            String s = "";

            // When
            var result = FSStringUtils.trimLeading(s);

            // Then
            assertEquals("", result);
        }

        @Test
        @DisplayName("Should return same string when input has no leading spaces")
        void shouldReturnSameStringWhenInputHasNoLeadingSpaces() {
            // Given
            String s = "one two";

            // When
            var result = FSStringUtils.trimLeading(s);

            // Then
            assertEquals("one two", result);
        }

        @Test
        @DisplayName("Should return same string when input has only trailing spaces")
        void shouldReturnSameStringWhenInputHasOnlyTrailingSpaces() {
            // Given
            String s = "one  two   ";

            // When
            var result = FSStringUtils.trimLeading(s);

            // Then
            assertEquals("one  two   ", result);
        }

        @Test
        @DisplayName("Should remove only leading spaces when input has both leading and trailing spaces")
        void shouldRemoveOnlyLeadingSpacesWhenInputHasBothLeadingAndTrailingSpaces() {
            // Given
            String s = "  one  two   ";

            // When
            var result = FSStringUtils.trimLeading(s);

            // Then
            assertEquals("one  two   ", result);
        }
    }
}
