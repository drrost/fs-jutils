package com.rdruzhchenko.fsjutils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FSFileSystemUtils {

    public static void createFile(String path, String content) throws IOException {
        Files.writeString(Paths.get(path), content);
    }

    public static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    public static void deleteDirectory(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    public static void createDirectory(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }

    public static String readResource(String path) throws IOException {
        ClassLoader classLoader = FSFileSystemUtils.class.getClassLoader();
        java.net.URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                return new String(Files.readAllBytes(
                        Paths.get(resource.toURI())), StandardCharsets.UTF_8);
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void copy(String source, String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);
        Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
    }
}
