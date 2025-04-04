package com.rdruzhchenko.fsjutils.string.alluppercased;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FSStringUtilsTest_allUpperCased {

    @Test
    public void testUppercased() {
        // Given
        var s = "МЕЛЬНИЧЕНКО";

        // When
        var result = FSStringUtils.isAllUpperCase(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void testSomeUppercased() {
        // Given
        var s = "МЕЛЬНИчЕНКО";

        // When
        var result = FSStringUtils.isAllUpperCase(s);

        // Then
        assertFalse(result);
    }

    @Test
    public void testLowercased() {
        // Given
        var s = "мельниченко";

        // When
        var result = FSStringUtils.isAllUpperCase(s);

        // Then
        assertFalse(result);
    }
}
