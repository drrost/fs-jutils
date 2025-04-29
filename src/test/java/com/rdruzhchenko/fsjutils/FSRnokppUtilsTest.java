package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FSRnokppUtils")
class FSRnokppUtilsTest {

    @Nested
    @DisplayName("RNOKPP validation tests")
    class RnokppValidationTests {

        @Test
        @DisplayName("Should return false when RNOKPP is empty")
        void shouldReturnFalseWhenRnokppIsEmpty() {
            // Given
            var rnokpp = "";

            // When
            var result = FSRnokppUtils.isValidRnokpp(rnokpp);

            // Then
            assertFalse(result);
        }
    }
}
