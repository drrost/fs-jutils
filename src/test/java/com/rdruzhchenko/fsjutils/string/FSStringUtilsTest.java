package com.rdruzhchenko.fsjutils.string;

import com.rdruzhchenko.fsjutils.FSStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FSStringUtilsTest {

    @Test
    void trimLeadingColon() {
        // Given
        var s = ":";

        // When
        s = FSStringUtils.trimLeadingColon(s);

        // Then
        assertEquals("", s);
    }

    @Test
    void trimLeadingColon2() {
        // Given
        var s = ": ";

        // When
        s = FSStringUtils.trimLeadingColon(s);

        // Then
        assertEquals(" ", s);
    }

    @Test
    void trimLeadingColon3() {
        // Given
        var s = ": diagnosis";

        // When
        s = FSStringUtils.trimLeadingColon(s);

        // Then
        assertEquals(" diagnosis", s);
    }

    @Test
    void trimLeadingColon4() {
        // Given
        var s = ": diagnosis";

        // When
        s = FSStringUtils.trimAllAndColon(s);

        // Then
        assertEquals("diagnosis", s);
    }

    @Test
    void trimLeadingColon5() {
        // Given
        var s = ": diagnosis:";

        // When
        s = FSStringUtils.trimAllAndColon(s);

        // Then
        assertEquals("diagnosis:", s);
    }
}