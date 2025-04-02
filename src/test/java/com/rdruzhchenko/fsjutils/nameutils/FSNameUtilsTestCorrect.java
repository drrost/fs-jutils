package com.rdruzhchenko.fsjutils.nameutils;

import com.rdruzhchenko.fsjutils.FSNameUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FSNameUtilsTestCorrect {

    @Test
    public void correct() {
        // Given
        String name = "Іван-Василь";

        // When
        String corrected = FSNameUtils.correct(name);

        // Then
        assertEquals(name, corrected);
    }

    @Test
    public void ogly() {
        // Given
        String name = "Мустафа-огли";

        // When
        String corrected = FSNameUtils.correct(name);

        // Then
        assertEquals(name, corrected);
    }

    @Test
    public void ogly2() {
        // Given
        String name = "Бахтіяр-огли";

        // When
        String corrected = FSNameUtils.correct(name);

        // Then
        assertEquals(name, corrected);
    }

    @Test
    public void daPrefix() {
        // Given
        String name = "Да Море";

        // When
        String corrected = FSNameUtils.correct(name);

        // Then
        assertEquals("да Море", corrected);
    }

    @Test
    public void daPrefix2() {
        // Given
        String name = "Да море";

        // When
        String corrected = FSNameUtils.correct(name);

        // Then
        assertEquals("да Море", corrected);
    }
}
