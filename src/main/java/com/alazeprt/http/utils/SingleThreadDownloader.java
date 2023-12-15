package com.alazeprt.http.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SingleThreadDownloader {
    public static int progress = -1;
    public static int maxProgress = -1;
    public static void downloadFile(String url, String filePath, Thread willStartThread) throws IOException {
        // Create URL and open connection
        URL downloadUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();

        // Check response code
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error downloading file: " + connection.getResponseCode());
        }

        // Get file size (optional)
        int fileSize = connection.getContentLength();

        progress = 0;
        maxProgress = fileSize;
        willStartThread.start();

        // Create output file
        File outputFile = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // Read and write data in chunks
        byte[] buffer = new byte[1024]; // Adjust buffer size as needed
        int bytesRead;
        long downloadedBytes = 0;

        while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            downloadedBytes += bytesRead;

            progress += bytesRead;
        }

        // Close resources
        outputStream.close();
        connection.disconnect();
    }
}
