package com.rdruzhchenko.fsjutils.nameutils;

import com.rdruzhchenko.fsjutils.string.FSNameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FSNameUtils - correct method")
class FSNameUtilsTestCorrect {

    @Nested
    @DisplayName("Name correction tests")
    class NameCorrectionTests {

        @Test
        @DisplayName("Should preserve hyphenated names")
        public void shouldPreserveHyphenatedNames() {
            // Given
            String name = "Іван-Василь";

            // When
            String corrected = FSNameUtils.correct(name);

            // Then
            assertEquals(name, corrected);
        }

        @Nested
        @DisplayName("Suffix tests")
        class SuffixTests {

            @Test
            @DisplayName("Should preserve names with -ogly suffix")
            public void shouldPreserveNamesWithOglySuffix() {
                // Given
                String name = "Мустафа-огли";

                // When
                String corrected = FSNameUtils.correct(name);

                // Then
                assertEquals(name, corrected);
            }

            @Test
            @DisplayName("Should preserve names with -ogly suffix (variant 2)")
            public void shouldPreserveNamesWithOglySuffixVariant2() {
                // Given
                String name = "Бахтіяр-огли";

                // When
                String corrected = FSNameUtils.correct(name);

                // Then
                assertEquals(name, corrected);
            }
        }

        @Nested
        @DisplayName("Prefix tests")
        class PrefixTests {

            @Test
            @DisplayName("Should correct 'Da' prefix to lowercase and capitalize following word")
            public void shouldCorrectDaPrefixToLowercaseAndCapitalizeFollowingWord() {
                // Given
                String name = "Да Море";

                // When
                String corrected = FSNameUtils.correct(name);

                // Then
                assertEquals("да Море", corrected);
            }

            @Test
            @DisplayName("Should correct 'Da' prefix to lowercase and capitalize following word (lowercase variant)")
            public void shouldCorrectDaPrefixToLowercaseAndCapitalizeFollowingWordLowercaseVariant() {
                // Given
                String name = "Да море";

                // When
                String corrected = FSNameUtils.correct(name);

                // Then
                assertEquals("да Море", corrected);
            }
        }
    }
}
