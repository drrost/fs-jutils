package com.rdruzhchenko.fsjutils.string.dropfirstword;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FSStringUtilsTestDropFirstWord {

    @Test
    void testDropFirstWordEmpty() {
        // Given
        String s = "";

        // When
        var result = FSStringUtils.dropFirstWord(s);

        // Then
        assertEquals("", result);
    }

    @Test
    void testDropFirstWordOneWord() {
        // Given
        String s = "one";

        // When
        var result = FSStringUtils.dropFirstWord(s);

        // Then
        assertEquals("", result);
    }

    @Test
    void testDropFirstWordTwoWords() {
        // Given
        String s = "one two ";

        // When
        var result = FSStringUtils.dropFirstWord(s);

        // Then
        assertEquals("two ", result);
    }

    @Test
    void testDropFirstWordTwoWordsLeadingSpace() {
        // Given
        String s = "  one two ";

        // When
        var result = FSStringUtils.dropFirstWord(s);

        // Then
        assertEquals("two ", result);
    }
}
