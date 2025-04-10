package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSDateUtilsTest {

    @Test
    public void test() {
        // Given
        var date = "no date";

        // When
        var result = FSDateUtils.isValidDate(date);

        // Then
        assertFalse(result);
    }

    @Test
    public void testLongerThanDate() {
        // Given
        var date = "22.06.2022,";

        // When
        var result = FSDateUtils.isValidDate(date);

        // Then
        assertFalse(result);
    }
}