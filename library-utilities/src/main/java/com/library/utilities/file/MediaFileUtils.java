package com.library.utilities.file;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MediaFileUtils {

    private MediaFileUtils() {
        throw new UnsupportedOperationException("You can't create instance of Utils class. Please use as static..");
    }

    /**
     * Generate random file name
     *
     * @return random file name
     */
    public static String getRandomFileName(int mediaType) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = timeStamp;

        if (mediaType == 1) {
            fileName = "IMG_" + timeStamp;
        }

        if (mediaType == 2) {
            fileName = "VID_" + timeStamp;
        }

        if (mediaType == 3) {
            fileName = "AUD_" + timeStamp;
        }

        if (mediaType == 4) {
            fileName = "PDF_" + timeStamp;
        }

        return fileName;
    }

    /**
     * Create directory
     *
     * @param context
     * @return directory path
     */
    public static File getMediaDir(@NonNull Context context, int mediaType, String customDirectoryName) {
        File extStorageDirectory = Environment.getExternalStorageDirectory();
        String rootDirectory = extStorageDirectory + "/" + customDirectoryName + "/";

        File mediaStorageDir = new File(rootDirectory);

        if (mediaType == 1) {
            String picturesPath = extStorageDirectory + "Pictures/";
            mediaStorageDir = new File(picturesPath);
        }

        if (mediaType == 2) {
            String videosPath = extStorageDirectory + "Videos/";
            mediaStorageDir = new File(videosPath);
        }

        if (mediaType == 3) {
            String audioPath = extStorageDirectory + "Audio/";
            mediaStorageDir = new File(audioPath);
        }

        if (mediaType == 4) {
            String pdfPath = extStorageDirectory + "PDF/";
            mediaStorageDir = new File(pdfPath);
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(context, "Oops! Failed to create directory", Toast.LENGTH_LONG).show();
                return null;
            }
        }
        return mediaStorageDir;
    }

    /**
     * Create file
     *
     * @param context
     * @param mediaType
     * @param customDirectoryName
     * @param extension
     * @param context
     * @return file
     * @throws IOException
     */
    public static File createFile(@NonNull Context context, int mediaType, String customDirectoryName, String extension) throws IOException {
        File mediaStorageDir = getMediaDir(context, mediaType, customDirectoryName);
        String fileName = getRandomFileName(mediaType);

        File imageTempFile = File.createTempFile(
                fileName,  /* prefix */
                extension,  /* suffix */
                mediaStorageDir     /* directory */
        );

        return imageTempFile;
    }
}
