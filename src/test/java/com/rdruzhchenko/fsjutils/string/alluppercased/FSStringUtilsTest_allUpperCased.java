package com.rdruzhchenko.fsjutils.string.alluppercased;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FSStringUtils - isAllUpperCase method")
public class FSStringUtilsTest_allUpperCased {

    @Nested
    @DisplayName("isAllUpperCase tests")
    class IsAllUpperCaseTests {

        @Test
        @DisplayName("Should return true when string is all uppercase")
        void shouldReturnTrueWhenStringIsAllUpperCase() {
            // Given
            var s = "МЕЛЬНИЧЕНКО";

            // When
            var result = FSStringUtils.isAllUpperCase(s);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("Should return false when string contains mixed case")
        void shouldReturnFalseWhenStringContainsMixedCase() {
            // Given
            var s = "МЕЛЬНИчЕНКО";

            // When
            var result = FSStringUtils.isAllUpperCase(s);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("Should return false when string is all lowercase")
        void shouldReturnFalseWhenStringIsAllLowerCase() {
            // Given
            var s = "мельниченко";

            // When
            var result = FSStringUtils.isAllUpperCase(s);

            // Then
            assertFalse(result);
        }
    }
}
