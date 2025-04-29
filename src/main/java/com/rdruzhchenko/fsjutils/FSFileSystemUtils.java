package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.exception.FSFileException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Utility class providing file system operations.
 * This class contains methods for creating, reading, deleting, and copying files and directories,
 * as well as reading resources from the classpath.
 */
public class FSFileSystemUtils {

    /**
     * Creates a new file with the specified content.
     *
     * @param path The path where the file should be created
     * @param content The content to write to the file
     * @throws IOException if an I/O error occurs during file creation
     */
    public static void createFile(String path, String content) throws IOException {
        Files.writeString(Paths.get(path), content);
    }

    /**
     * Reads the content of a file.
     *
     * @param path The path of the file to read
     * @return The content of the file as a string
     * @throws IOException if an I/O error occurs during file reading
     */
    public static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    /**
     * Deletes a file if it exists.
     *
     * @param path The path of the file to delete
     * @throws IOException if an I/O error occurs during file deletion
     */
    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    /**
     * Deletes a directory if it exists.
     *
     * @param path The path of the directory to delete
     * @throws IOException if an I/O error occurs during directory deletion
     */
    public static void deleteDirectory(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    /**
     * Creates a directory and any necessary parent directories.
     *
     * @param path The path of the directory to create
     * @throws IOException if an I/O error occurs during directory creation
     */
    public static void createDirectory(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }

    /**
     * Reads a resource file from the classpath.
     *
     * @param path The path of the resource file relative to the classpath
     * @return The content of the resource file as a string
     * @throws IOException if an I/O error occurs during resource reading
     * @throws FSFileException if the resource file is not found or cannot be read
     */
    public static String readResource(String path) throws IOException {
        ClassLoader classLoader = FSFileSystemUtils.class.getClassLoader();
        java.net.URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new FSFileException("Resource file not found: " + path);
        } else {
            try {
                return new String(Files.readAllBytes(
                        Paths.get(resource.toURI())), StandardCharsets.UTF_8);
            } catch (IOException | java.net.URISyntaxException e) {
                throw new FSFileException("Failed to read resource file: " + path, e);
            }
        }
    }

    /**
     * Copies a file from the source path to the target path, replacing the target if it exists.
     *
     * @param source The path of the source file
     * @param target The path where the file should be copied to
     * @throws IOException if an I/O error occurs during file copying
     */
    public static void copy(String source, String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);
        Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
    }
}
