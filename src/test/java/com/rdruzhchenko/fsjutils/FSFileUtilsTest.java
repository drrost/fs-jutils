package com.rdruzhchenko.fsjutils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FSFileUtils class.
 */
@DisplayName("FSFileUtils")
class FSFileUtilsTest {

    @Nested
    @DisplayName("download")
    class Download {

        @Test
        @DisplayName("Should download file from valid URL")
        void shouldDownloadFileFromValidUrl() throws IOException {
            // Given
            String fileUrl = "https://www.example.com/"; // Example.com is a stable test site

            // When
            byte[] result = FSFileUtils.download(fileUrl);

            // Then
            assertNotNull(result, "Downloaded content should not be null");
            assertTrue(result.length > 0, "Downloaded content should not be empty");
        }

        @Test
        @DisplayName("Should throw MalformedURLException for invalid URL")
        void shouldThrowMalformedURLExceptionForInvalidUrl() {
            // Given
            String invalidUrl = "not-a-valid-url";

            // When & Then
            assertThrows(MalformedURLException.class, () -> FSFileUtils.download(invalidUrl),
                    "Should throw MalformedURLException for invalid URL");
        }

        @Test
        @DisplayName("Should throw IOException for non-existent URL")
        void shouldThrowIOExceptionForNonExistentUrl() {
            // Given
            String nonExistentUrl = "https://this-url-does-not-exist-12345.com/";

            // When & Then
            assertThrows(IOException.class, () -> FSFileUtils.download(nonExistentUrl),
                    "Should throw IOException for non-existent URL");
        }

        @Test
        @DisplayName("Should handle null URL")
        void shouldHandleNullUrl() {
            // Given
            String nullUrl = null;

            // When & Then
            assertThrows(MalformedURLException.class, () -> FSFileUtils.download(nullUrl),
                    "Should throw MalformedURLException for null URL");
        }
    }
}
