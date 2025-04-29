package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.random.FSRandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FSRandomUtils class.
 */
@DisplayName("FSRandomUtils")
class FSRandomUtilsTest {

    // Sample enum for testing randomEnum method
    private enum TestEnum {
        VALUE1, VALUE2, VALUE3, VALUE4, VALUE5
    }

    @Nested
    @DisplayName("randInt with min and max")
    class RandIntWithMinMax {
        
        @RepeatedTest(100)
        @DisplayName("Should generate integer within specified range")
        void shouldGenerateIntegerWithinSpecifiedRange() {
            // Given
            int min = 10;
            int max = 100;
            
            // When
            int result = FSRandomUtils.randInt(min, max);
            
            // Then
            assertTrue(result >= min && result <= max, 
                    "Generated value " + result + " should be between " + min + " and " + max);
        }
        
        @Test
        @DisplayName("Should handle min equal to max")
        void shouldHandleMinEqualToMax() {
            // Given
            int value = 42;
            
            // When
            int result = FSRandomUtils.randInt(value, value);
            
            // Then
            assertEquals(value, result, "When min equals max, the result should be that value");
        }
        
        @Test
        @DisplayName("Should handle negative ranges")
        void shouldHandleNegativeRanges() {
            // Given
            int min = -100;
            int max = -10;
            
            // When
            int result = FSRandomUtils.randInt(min, max);
            
            // Then
            assertTrue(result >= min && result <= max, 
                    "Generated value " + result + " should be between " + min + " and " + max);
        }
        
        @Test
        @DisplayName("Should handle ranges crossing zero")
        void shouldHandleRangesCrossingZero() {
            // Given
            int min = -50;
            int max = 50;
            
            // When
            int result = FSRandomUtils.randInt(min, max);
            
            // Then
            assertTrue(result >= min && result <= max, 
                    "Generated value " + result + " should be between " + min + " and " + max);
        }
    }
    
    @Nested
    @DisplayName("randInt without parameters")
    class RandIntWithoutParameters {
        
        @RepeatedTest(100)
        @DisplayName("Should generate integer within default range")
        void shouldGenerateIntegerWithinDefaultRange() {
            // When
            int result = FSRandomUtils.randInt();
            
            // Then
            assertTrue(result >= 0 && result <= 20000, 
                    "Generated value " + result + " should be between 0 and 20000");
        }
    }
    
    @Nested
    @DisplayName("randUuid")
    class RandUuid {
        
        @RepeatedTest(10)
        @DisplayName("Should generate valid UUID string")
        void shouldGenerateValidUuidString() {
            // When
            String result = FSRandomUtils.randUuid();
            
            // Then
            assertNotNull(result, "UUID should not be null");
            assertEquals(36, result.length(), "UUID string should be 36 characters long");
            assertTrue(result.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"), 
                    "UUID should match the standard format");
            
            // Verify it's a valid UUID by parsing it
            assertDoesNotThrow(() -> UUID.fromString(result), "Should be parseable as a UUID");
        }
        
        @Test
        @DisplayName("Should generate unique UUIDs")
        void shouldGenerateUniqueUuids() {
            // Given
            int count = 1000;
            Set<String> uuids = new HashSet<>();
            
            // When
            for (int i = 0; i < count; i++) {
                uuids.add(FSRandomUtils.randUuid());
            }
            
            // Then
            assertEquals(count, uuids.size(), "All generated UUIDs should be unique");
        }
    }
    
    @Nested
    @DisplayName("randBoolean")
    class RandBoolean {
        
        @Test
        @DisplayName("Should generate both true and false values")
        void shouldGenerateBothTrueAndFalseValues() {
            // Given
            int count = 1000;
            boolean foundTrue = false;
            boolean foundFalse = false;
            
            // When & Then
            for (int i = 0; i < count && (!foundTrue || !foundFalse); i++) {
                boolean result = FSRandomUtils.randBoolean();
                if (result) {
                    foundTrue = true;
                } else {
                    foundFalse = true;
                }
            }
            
            assertTrue(foundTrue, "Should generate at least one true value");
            assertTrue(foundFalse, "Should generate at least one false value");
        }
    }
    
    @Nested
    @DisplayName("randomEnum")
    class RandomEnum {
        
        @Test
        @DisplayName("Should return a valid enum value")
        void shouldReturnValidEnumValue() {
            // When
            TestEnum result = FSRandomUtils.randomEnum(TestEnum.class);
            
            // Then
            assertNotNull(result, "Enum value should not be null");
        }
        
        @Test
        @DisplayName("Should generate all possible enum values")
        void shouldGenerateAllPossibleEnumValues() {
            // Given
            int count = 1000;
            Set<TestEnum> generatedValues = new HashSet<>();
            
            // When
            for (int i = 0; i < count; i++) {
                generatedValues.add(FSRandomUtils.randomEnum(TestEnum.class));
            }
            
            // Then
            assertEquals(TestEnum.values().length, generatedValues.size(), 
                    "Should generate all possible enum values");
        }
    }
    
    @Nested
    @DisplayName("randString")
    class RandString {
        
        @RepeatedTest(100)
        @DisplayName("Should generate alphanumeric string with valid length")
        void shouldGenerateAlphanumericStringWithValidLength() {
            // When
            String result = FSRandomUtils.randString();
            
            // Then
            assertNotNull(result, "String should not be null");
            assertTrue(result.length() >= 1 && result.length() <= 50, 
                    "String length should be between 1 and 50");
            assertTrue(result.matches("[a-zA-Z0-9]+"), 
                    "String should contain only alphanumeric characters");
        }
    }
    
    @Nested
    @DisplayName("randDate")
    class RandDate {
        
        @RepeatedTest(10)
        @DisplayName("Should generate a string")
        void shouldGenerateString() {
            // When
            String result = FSRandomUtils.randDate();
            
            // Then
            assertNotNull(result, "Date string should not be null");
            assertTrue(result.length() >= 1 && result.length() <= 50, 
                    "Date string length should be between 1 and 50");
        }
    }
    
    @Nested
    @DisplayName("randBool")
    class RandBool {
        
        @Test
        @DisplayName("Should generate both true and false values")
        void shouldGenerateBothTrueAndFalseValues() {
            // Given
            int count = 1000;
            boolean foundTrue = false;
            boolean foundFalse = false;
            
            // When & Then
            for (int i = 0; i < count && (!foundTrue || !foundFalse); i++) {
                boolean result = FSRandomUtils.randBool();
                if (result) {
                    foundTrue = true;
                } else {
                    foundFalse = true;
                }
            }
            
            assertTrue(foundTrue, "Should generate at least one true value");
            assertTrue(foundFalse, "Should generate at least one false value");
        }
    }
    
    @Nested
    @DisplayName("randBytes")
    class RandBytes {
        
        @RepeatedTest(10)
        @DisplayName("Should generate byte array with valid length")
        void shouldGenerateByteArrayWithValidLength() {
            // When
            byte[] result = FSRandomUtils.randBytes();
            
            // Then
            assertNotNull(result, "Byte array should not be null");
            assertTrue(result.length >= 10 && result.length <= 100, 
                    "Byte array length should be between 10 and 100");
        }
        
        @Test
        @DisplayName("Should generate different byte arrays")
        void shouldGenerateDifferentByteArrays() {
            // When
            byte[] result1 = FSRandomUtils.randBytes();
            byte[] result2 = FSRandomUtils.randBytes();
            
            // Then
            // Check if arrays are different (either by length or content)
            boolean different = result1.length != result2.length;
            if (!different) {
                for (int i = 0; i < result1.length; i++) {
                    if (result1[i] != result2[i]) {
                        different = true;
                        break;
                    }
                }
            }
            
            assertTrue(different, "Generated byte arrays should be different");
        }
    }
}