package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the FSPhoneNumberFormatter class.
 */
@DisplayName("FSPhoneNumberFormatter")
class FSPhoneNumberFormatterTest {

    @Nested
    @DisplayName("format")
    class Format {
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "  ", "\t", "\n"})
        @DisplayName("Should return input when null or blank")
        void shouldReturnInputWhenNullOrBlank(String input) {
            // When
            String result = FSPhoneNumberFormatter.format(input);
            
            // Then
            assertEquals(input, result, "Should return the original input when null or blank");
        }
        
        @Test
        @DisplayName("Should remove non-digit characters")
        void shouldRemoveNonDigitCharacters() {
            // Given
            String phoneNumber = "123-456-7890";
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals("+381234567890", result, "Should remove non-digit characters and add +38 prefix");
        }
        
        @Test
        @DisplayName("Should add +38 prefix to 10-digit numbers")
        void shouldAddPrefixTo10DigitNumbers() {
            // Given
            String phoneNumber = "0501234567";
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals("+380501234567", result, "Should add +38 prefix to 10-digit numbers");
        }
        
        @Test
        @DisplayName("Should preserve existing + sign")
        void shouldPreserveExistingPlusSign() {
            // Given
            String phoneNumber = "+380501234567";
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals("+380501234567", result, "Should preserve existing + sign");
        }
        
        @Test
        @DisplayName("Should return original input for non-10-digit numbers without + sign")
        void shouldReturnOriginalInputForNon10DigitNumbersWithoutPlusSign() {
            // Given
            String phoneNumber = "12345"; // Less than 10 digits
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals(phoneNumber, result, "Should return original input for numbers with less than 10 digits");
        }
        
        @Test
        @DisplayName("Should return original input for numbers with more than 10 digits without + sign")
        void shouldReturnOriginalInputForNumbersWithMoreThan10DigitsWithoutPlusSign() {
            // Given
            String phoneNumber = "12345678901234"; // More than 10 digits
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals(phoneNumber, result, "Should return original input for numbers with more than 10 digits");
        }
        
        @Test
        @DisplayName("Should handle phone numbers with spaces and other separators")
        void shouldHandlePhoneNumbersWithSpacesAndOtherSeparators() {
            // Given
            String phoneNumber = "+38 (050) 123-45-67";
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals("+380501234567", result, "Should remove spaces and other separators");
        }
        
        @Test
        @DisplayName("Should handle phone numbers with country code but without + sign")
        void shouldHandlePhoneNumbersWithCountryCodeButWithoutPlusSign() {
            // Given
            String phoneNumber = "380501234567";
            
            // When
            String result = FSPhoneNumberFormatter.format(phoneNumber);
            
            // Then
            assertEquals(phoneNumber, result, "Should return original input for numbers with more than 10 digits");
        }
    }
}