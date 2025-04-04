package com.rdruzhchenko.fsjutils.string.capitalized;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FSStringUtilsTest_capitalized {

    @Test
    public void testCapitalized() {
        // Given
        var s = "Іван";

        // When
        var result = FSStringUtils.isCapitalized(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testUppercased() {
        // Given
        var s = "ІВАН";

        // When
        var result = FSStringUtils.isCapitalized(s);

        // Then
        assertFalse(result);
    }

    @Test
    public void testLowercased() {
        // Given
        var s = "іван";

        // When
        var result = FSStringUtils.isCapitalized(s);

        // Then
        assertFalse(result);
    }

    @Test
    public void testCapitalizedWrong() {
        // Given
        var s = "іВан";

        // When
        var result = FSStringUtils.isCapitalized(s);

        // Then
        assertFalse(result);
    }
}
