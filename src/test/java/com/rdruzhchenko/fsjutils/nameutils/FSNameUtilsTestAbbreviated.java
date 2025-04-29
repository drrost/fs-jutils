package com.rdruzhchenko.fsjutils.nameutils;

import com.rdruzhchenko.fsjutils.string.FSNameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("FSNameUtils - abbreviatedFromFull method")
public class FSNameUtilsTestAbbreviated {

    @Nested
    @DisplayName("abbreviatedFromFull tests")
    class AbbreviatedFromFullTests {

        @Test
        @DisplayName("Should return null when input is null")
        public void shouldReturnNullWhenInputIsNull() {
            // Given
            String fullName = null;

            // When
            var result = FSNameUtils.abbreviatedFromFull(fullName);

            // Then
            assertNull(result);
        }

        @Test
        @DisplayName("Should return same name when input has only one part")
        public void shouldReturnSameNameWhenInputHasOnlyOnePart() {
            // Given
            String fullName = "Іван";

            // When
            var result = FSNameUtils.abbreviatedFromFull(fullName);

            // Then
            assertEquals(fullName, result);
        }

        @Test
        @DisplayName("Should abbreviate first name when input has two parts")
        public void shouldAbbreviateFirstNameWhenInputHasTwoParts() {
            // Given
            String fullName = "Василишин Іван";

            // When
            var result = FSNameUtils.abbreviatedFromFull(fullName);

            // Then
            assertEquals("Василишин І.", result);
        }

        @Test
        @DisplayName("Should abbreviate first and middle names when input has three parts")
        public void shouldAbbreviateFirstAndMiddleNamesWhenInputHasThreeParts() {
            // Given
            String fullName = "Василишин Іван Михайлович";

            // When
            var result = FSNameUtils.abbreviatedFromFull(fullName);

            // Then
            assertEquals("Василишин І.М.", result);
        }
    }
}
