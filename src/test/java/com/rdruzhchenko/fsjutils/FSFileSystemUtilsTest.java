package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.exception.FSFileException;
import com.rdruzhchenko.fsjutils.file.FSFileSystemUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FSFileSystemUtils class.
 */
@DisplayName("FSFileSystemUtils")
class FSFileSystemUtilsTest {

    @TempDir
    Path tempDir;

    @Nested
    @DisplayName("createFile")
    class CreateFile {

        @Test
        @DisplayName("Should create file with content")
        void shouldCreateFileWithContent() throws IOException {
            // Given
            Path filePath = tempDir.resolve("test-file.txt");
            String content = "Test content";

            // When
            FSFileSystemUtils.createFile(filePath.toString(), content);

            // Then
            assertTrue(Files.exists(filePath), "File should exist");
            assertEquals(content, Files.readString(filePath), "File content should match");
        }

        @Test
        @DisplayName("Should throw InvalidPathException for invalid path")
        void shouldThrowInvalidPathExceptionForInvalidPath() {
            // Given
            String invalidPath = "\0invalid"; // Null character is invalid in file paths
            String content = "Test content";

            // When & Then
            assertThrows(java.nio.file.InvalidPathException.class, () -> FSFileSystemUtils.createFile(invalidPath, content),
                    "Should throw InvalidPathException for invalid path");
        }
    }

    @Nested
    @DisplayName("readFile")
    class ReadFile {

        @Test
        @DisplayName("Should read file content")
        void shouldReadFileContent() throws IOException {
            // Given
            Path filePath = tempDir.resolve("test-file.txt");
            String content = "Test content";
            Files.writeString(filePath, content);

            // When
            String result = FSFileSystemUtils.readFile(filePath.toString());

            // Then
            assertEquals(content, result, "Read content should match original content");
        }

        @Test
        @DisplayName("Should throw IOException for non-existent file")
        void shouldThrowIOExceptionForNonExistentFile() {
            // Given
            Path nonExistentPath = tempDir.resolve("non-existent-file.txt");

            // When & Then
            assertThrows(IOException.class, () -> FSFileSystemUtils.readFile(nonExistentPath.toString()),
                    "Should throw IOException for non-existent file");
        }
    }

    @Nested
    @DisplayName("deleteFile")
    class DeleteFile {

        @Test
        @DisplayName("Should delete existing file")
        void shouldDeleteExistingFile() throws IOException {
            // Given
            Path filePath = tempDir.resolve("test-file.txt");
            Files.writeString(filePath, "Test content");

            // When
            FSFileSystemUtils.deleteFile(filePath.toString());

            // Then
            assertFalse(Files.exists(filePath), "File should be deleted");
        }

        @Test
        @DisplayName("Should not throw exception for non-existent file")
        void shouldNotThrowExceptionForNonExistentFile() {
            // Given
            Path nonExistentPath = tempDir.resolve("non-existent-file.txt");

            // When & Then
            assertDoesNotThrow(() -> FSFileSystemUtils.deleteFile(nonExistentPath.toString()),
                    "Should not throw exception for non-existent file");
        }
    }

    @Nested
    @DisplayName("deleteDirectory")
    class DeleteDirectory {

        @Test
        @DisplayName("Should delete empty directory")
        void shouldDeleteEmptyDirectory() throws IOException {
            // Given
            Path dirPath = tempDir.resolve("test-dir");
            Files.createDirectory(dirPath);

            // When
            FSFileSystemUtils.deleteDirectory(dirPath.toString());

            // Then
            assertFalse(Files.exists(dirPath), "Directory should be deleted");
        }

        @Test
        @DisplayName("Should not throw exception for non-existent directory")
        void shouldNotThrowExceptionForNonExistentDirectory() {
            // Given
            Path nonExistentPath = tempDir.resolve("non-existent-dir");

            // When & Then
            assertDoesNotThrow(() -> FSFileSystemUtils.deleteDirectory(nonExistentPath.toString()),
                    "Should not throw exception for non-existent directory");
        }
    }

    @Nested
    @DisplayName("createDirectory")
    class CreateDirectory {

        @Test
        @DisplayName("Should create directory")
        void shouldCreateDirectory() throws IOException {
            // Given
            Path dirPath = tempDir.resolve("test-dir");

            // When
            FSFileSystemUtils.createDirectory(dirPath.toString());

            // Then
            assertTrue(Files.exists(dirPath), "Directory should exist");
            assertTrue(Files.isDirectory(dirPath), "Path should be a directory");
        }

        @Test
        @DisplayName("Should create nested directories")
        void shouldCreateNestedDirectories() throws IOException {
            // Given
            Path dirPath = tempDir.resolve("parent/child/grandchild");

            // When
            FSFileSystemUtils.createDirectory(dirPath.toString());

            // Then
            assertTrue(Files.exists(dirPath), "Nested directories should exist");
            assertTrue(Files.isDirectory(dirPath), "Path should be a directory");
        }
    }

    @Nested
    @DisplayName("copy")
    class Copy {

        @Test
        @DisplayName("Should copy file")
        void shouldCopyFile() throws IOException {
            // Given
            Path sourcePath = tempDir.resolve("source.txt");
            Path targetPath = tempDir.resolve("target.txt");
            String content = "Test content";
            Files.writeString(sourcePath, content);

            // When
            FSFileSystemUtils.copy(sourcePath.toString(), targetPath.toString());

            // Then
            assertTrue(Files.exists(targetPath), "Target file should exist");
            assertEquals(content, Files.readString(targetPath), "Target file content should match source");
        }

        @Test
        @DisplayName("Should throw IOException for non-existent source")
        void shouldThrowIOExceptionForNonExistentSource() {
            // Given
            Path nonExistentSource = tempDir.resolve("non-existent-source.txt");
            Path targetPath = tempDir.resolve("target.txt");

            // When & Then
            assertThrows(IOException.class, () -> FSFileSystemUtils.copy(nonExistentSource.toString(), targetPath.toString()),
                    "Should throw IOException for non-existent source");
        }

        @Test
        @DisplayName("Should replace existing target")
        void shouldReplaceExistingTarget() throws IOException {
            // Given
            Path sourcePath = tempDir.resolve("source.txt");
            Path targetPath = tempDir.resolve("target.txt");
            String sourceContent = "Source content";
            String targetContent = "Target content";
            Files.writeString(sourcePath, sourceContent);
            Files.writeString(targetPath, targetContent);

            // When
            FSFileSystemUtils.copy(sourcePath.toString(), targetPath.toString());

            // Then
            assertTrue(Files.exists(targetPath), "Target file should exist");
            assertEquals(sourceContent, Files.readString(targetPath), "Target file content should be replaced with source content");
        }
    }

    @Nested
    @DisplayName("readResource")
    class ReadResource {

        @Test
        @DisplayName("Should throw FSFileException for non-existent resource")
        void shouldThrowFSFileExceptionForNonExistentResource() {
            // Given
            String nonExistentResource = "non-existent-resource.txt";

            // When & Then
            assertThrows(FSFileException.class, () -> FSFileSystemUtils.readResource(nonExistentResource),
                    "Should throw FSFileException for non-existent resource");
        }
    }
}
