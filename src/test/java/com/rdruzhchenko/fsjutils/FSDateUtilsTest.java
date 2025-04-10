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
}