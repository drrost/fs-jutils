package com.rdruzhchenko.fsjutils.string.capitalized;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FSStringUtils - isCapitalized method")
public class FSStringUtilsTest_capitalized {

    @Nested
    @DisplayName("isCapitalized tests")
    class IsCapitalizedTests {

        @Test
        @DisplayName("Should return true for properly capitalized string")
        public void shouldReturnTrueForProperlyCapitalizedString() {
            // Given
            var s = "Іван";

            // When
            var result = FSStringUtils.isCapitalized(s);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false for all uppercase string")
        public void shouldReturnFalseForAllUppercaseString() {
            // Given
            var s = "ІВАН";

            // When
            var result = FSStringUtils.isCapitalized(s);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false for all lowercase string")
        public void shouldReturnFalseForAllLowercaseString() {
            // Given
            var s = "іван";

            // When
            var result = FSStringUtils.isCapitalized(s);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false for incorrectly capitalized string")
        public void shouldReturnFalseForIncorrectlyCapitalizedString() {
            // Given
            var s = "іВан";

            // When
            var result = FSStringUtils.isCapitalized(s);

            // Then
            assertFalse(result);
        }
    }
}
