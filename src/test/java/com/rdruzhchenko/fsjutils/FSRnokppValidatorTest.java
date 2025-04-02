package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSRnokppValidatorTest {

    @Test
    public void testEmpty() {
        // Given
        var rnokpp = "";

        // When
        var result = FSRnokppValidator.isValidRnokpp(rnokpp);

        // Then
        assertFalse(result);
    }
}