package com.rdruzhchenko.fsjutils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FSFileUtils {

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
