package com.rdruzhchenko.fsjutils.nameutils;

import com.rdruzhchenko.fsjutils.FSNameUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FSNameUtilsTestAbbreviated {

    @Test
    public void testAbbreviatedFromFull_Null() {
        // Given
        String fullName = null;

        // When
        var result = FSNameUtils.abbreviatedFromFull(fullName);

        // Then
        assertNull(result);
    }

    @Test
    public void testAbbreviatedFromFull_OnePart() {
        // Given
        String fullName = "Іван";

        // When
        var result = FSNameUtils.abbreviatedFromFull(fullName);

        // Then
        assertEquals(fullName, result);
    }

    @Test
    public void testAbbreviatedFromFull_TwoParts() {
        // Given
        String fullName = "Василишин Іван";

        // When
        var result = FSNameUtils.abbreviatedFromFull(fullName);

        // Then
        assertEquals("Василишин І.", result);
    }

    @Test
    public void testAbbreviatedFromFull_ThreeParts() {
        // Given
        String fullName = "Василишин Іван Михайлович";

        // When
        var result = FSNameUtils.abbreviatedFromFull(fullName);

        // Then
        assertEquals("Василишин І.М.", result);
    }
}
