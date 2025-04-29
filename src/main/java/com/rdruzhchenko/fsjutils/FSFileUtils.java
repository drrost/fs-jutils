package com.rdruzhchenko.fsjutils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class providing file-related operations.
 * This class contains methods for downloading files from URLs.
 */
public class FSFileUtils {

    /**
     * Downloads a file from a URL and returns its contents as a byte array.
     *
     * @param fileUrl The URL of the file to download
     * @return The file contents as a byte array
     * @throws IOException if an I/O error occurs during the download
     */
    public static byte[] download(String fileUrl) throws IOException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();
            byte[] byteChunk = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(byteChunk)) != -1) {
                outputStream.write(byteChunk, 0, bytesRead);
            }

            inputStream.close();
            return outputStream.toByteArray();
        }
    }
}
