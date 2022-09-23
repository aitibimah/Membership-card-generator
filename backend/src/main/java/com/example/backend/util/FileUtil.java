package com.example.backend.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * @return a unique number
     */
    public static String getUniqueFileName() {
        return new Date().getTime() + "" + (int) (Math.random() * Integer.MAX_VALUE);
    }

    /**
     * saves  given base64 image to the given path <br>
     * this method supports only jpg images
     * @param path
     * @param base64Image
     */
    public static void saveImageToFileSystem(String path, String base64Image) {
        logger.info(">> saving image to path: " + path);
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            base64Image = base64Image.replace("data:image/jpeg;base64,", ""); // remove non base64 characters
            byte[] dataBytes = Base64.getDecoder().decode(base64Image);
            fileOutputStream.write(dataBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.info("failed to close the stream");
                }
        }
    }

    /**
     * deletes the file at the given path
     * @param path the path of the file  that will be deleted
     */
    public static void deleteImageFromFileSystem(String path) {
        logger.info(">> starting to delete image with path: " + path);
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}