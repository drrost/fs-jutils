package com.rdruzhchenko.fsjutils.string;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FSStringUtils")
class FSStringUtilsTest {

    @Nested
    @DisplayName("trimLeadingColon tests")
    class TrimLeadingColonTests {

        @Test
        @DisplayName("Should return empty string when input is only a colon")
        void shouldReturnEmptyStringWhenInputIsOnlyAColon() {
            // Given
            var s = ":";

            // When
            s = FSStringUtils.trimLeadingColon(s);

            // Then
            assertEquals("", s);
        }

        @Test
        @DisplayName("Should return space when input is colon followed by space")
        void shouldReturnSpaceWhenInputIsColonFollowedBySpace() {
            // Given
            var s = ": ";

            // When
            s = FSStringUtils.trimLeadingColon(s);

            // Then
            assertEquals(" ", s);
        }

        @Test
        @DisplayName("Should return text with leading space when input is colon followed by space and text")
        void shouldReturnTextWithLeadingSpaceWhenInputIsColonFollowedBySpaceAndText() {
            // Given
            var s = ": diagnosis";

            // When
            s = FSStringUtils.trimLeadingColon(s);

            // Then
            assertEquals(" diagnosis", s);
        }
    }

    @Nested
    @DisplayName("trimAllAndColon tests")
    class TrimAllAndColonTests {

        @Test
        @DisplayName("Should return text without leading space when trimming all and colon")
        void shouldReturnTextWithoutLeadingSpaceWhenTrimmingAllAndColon() {
            // Given
            var s = ": diagnosis";

            // When
            s = FSStringUtils.trimAllAndColon(s);

            // Then
            assertEquals("diagnosis", s);
        }

        @Test
        @DisplayName("Should preserve trailing colon when trimming all and leading colon")
        void shouldPreserveTrailingColonWhenTrimmingAllAndLeadingColon() {
            // Given
            var s = ": diagnosis:";

            // When
            s = FSStringUtils.trimAllAndColon(s);

            // Then
            assertEquals("diagnosis:", s);
        }
    }
}
