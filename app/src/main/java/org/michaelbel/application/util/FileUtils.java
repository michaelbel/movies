package org.michaelbel.application.util;

import android.support.annotation.NonNull;

import org.michaelbel.application.moviemade.AppLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@SuppressWarnings("all")
public class FileUtils {

    public static File getFileByPath(@NonNull String path) {
        return new File(path);
    }

    public static boolean makeDirectory(@NonNull String path) {
        File directory = new File(path);
        return !directory.exists() && directory.mkdirs();
    }

    public static void copyFile(File source, File destination) throws IOException {
        FileInputStream fromFile = new FileInputStream(source);
        FileOutputStream toFile = new FileOutputStream(destination);
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    private static boolean createCacheDirectory(@NonNull String directoryName) {
        File directory = new File(AppLoader.AppContext.getCacheDir(), directoryName);
        if (!directory.exists()) {
            return directory.mkdir();
        }

        return directory.exists();
    }
}