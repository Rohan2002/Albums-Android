package com.example.androidphotos59.utils;

import java.io.File;
import java.io.IOException;

/**
 * General common utilities function used across the app codebase.
 * 
 * @author Rohan Deshpande, Saman Sathenjeri
 * @version 1.0
 */
public class Tools {
    /**
     * Get the data directory from anywhere in the project.
     * @return The File object that represents the main data directory of the app.
     * @throws IOException If the data directory is not found.
     */
    public static File getDataDir() throws IOException {
        String projectRootDirectory = System.getProperty("user.dir");

        String dataDirectory = projectRootDirectory + File.separator + "data";

        File dataDirFileObj = new File(dataDirectory);
        if (!dataDirFileObj.isDirectory()) {
            throw new IOException("Main data directory does not exist!");
        }

        return dataDirFileObj;
    }

    /**
     * Recursively delete a directory.
     * @param directory to delete.
     * @return True if the directory is deleted else False
     */
    public static boolean deleteDirectory(File directory) {
        if (directory == null){
            return true;
        }
        if (!directory.isDirectory()) {
            return false;
        }
        // Get all files and subdirectories in the directory
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                // Recursive delete for subdirectories
                deleteDirectory(file);
            }
        }

        // Finally, delete the empty directory
        return directory.delete();
    }
}
