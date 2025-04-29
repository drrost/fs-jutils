package com.rdruzhchenko.fsjutils.string.dropfirstword;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FSStringUtils - dropFirstWord method")
public class FSStringUtilsTestDropFirstWord {

    @Nested
    @DisplayName("dropFirstWord tests")
    class DropFirstWordTests {

        @Test
        @DisplayName("Should return empty string when input is empty")
        void shouldReturnEmptyStringWhenInputIsEmpty() {
            // Given
            String s = "";

            // When
            var result = FSStringUtils.dropFirstWord(s);

            // Then
            assertEquals("", result);
        }

        @Test
        @DisplayName("Should return empty string when input has only one word")
        void shouldReturnEmptyStringWhenInputHasOnlyOneWord() {
            // Given
            String s = "one";

            // When
            var result = FSStringUtils.dropFirstWord(s);

            // Then
            assertEquals("", result);
        }

        @Test
        @DisplayName("Should return second word when input has two words")
        void shouldReturnSecondWordWhenInputHasTwoWords() {
            // Given
            String s = "one two ";

            // When
            var result = FSStringUtils.dropFirstWord(s);

            // Then
            assertEquals("two ", result);
        }

        @Test
        @DisplayName("Should handle leading spaces correctly when dropping first word")
        void shouldHandleLeadingSpacesCorrectlyWhenDroppingFirstWord() {
            // Given
            String s = "  one two ";

            // When
            var result = FSStringUtils.dropFirstWord(s);

            // Then
            assertEquals("two ", result);
        }
    }
}
