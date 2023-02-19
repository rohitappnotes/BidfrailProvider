package com.library.utilities.file;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.math.BigDecimal;

public class MemoryUtils {

    public static final String TAG = MemoryUtils.class.getSimpleName();

    /**
     * Unit of memory start from byte, 1024 bytes = 1KB
     */
    public static final int BYTE = 1;

    /**
     * Multiple of KB
     *
     * e.g.,
     *          1024 * 1 = 1KB
     *          1024 * 10 = 10KB
     *          1024 * 9 = 9KB
     */
    public static final int KB = 1024;

    /**
     * Multiple of MB
     *
     * e.g.,
     *          1048576 * 1 = 1GB
     *          1048576 * 10 = 10GB
     *          1048576 * 9 = 9GB
     */
    public static final int MB = 1048576;

    /**
     * Multiple of GB
     *
     * e.g.,
     *          1073741824 * 1 = 1GB
     *          1073741824 * 10 = 10GB
     *          1073741824 * 9 = 9GB
     */
    public static final int GB = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    /* Storage states */
    public static boolean externalStorageAvailable, externalStorageWriteable;

    private MemoryUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Checks the external storage's state and saves it in member attributes.
     */
    public static boolean isSDCardPresent() {
        /* Get the external storage's state */
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            /* Storage is available and writeable */
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            /* Storage is only readable */
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            /* Storage is neither readable nor writeable */
            externalStorageAvailable = externalStorageWriteable = false;
        }
        return externalStorageWriteable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is available, false otherwise.
     */
    public static boolean isExternalStorageAvailable() {
        isSDCardPresent();
        return externalStorageAvailable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is writeable, false otherwise.
     */
    public static boolean isExternalStorageWriteable() {
        isSDCardPresent();
        return externalStorageWriteable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is available and writeable, false
     * otherwise.
     */
    public static boolean isExternalStorageAvailableAndWriteable() {
        isSDCardPresent();
        if (!externalStorageAvailable) {
            return false;
        } else if (!externalStorageWriteable) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the file size in a human-readable string.
     *
     * @param size the size passed in
     * @return formatting unit returns the value after formatting
     */
    public static String getReadableFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }

    /**
     * converting long duration to  hh:mm:ss
     *
     * @param millie
     */
    public static String convertMillieToHMmSs(long millie) {
        String audioTime;

        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        if (hour > 0) {
            audioTime = String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            audioTime = String.format("%02d:%02d" , minute, second);
        }
        return audioTime;
    }

    /**
     * Get the amount of free space inside the phone
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();// Get Android data directory
        StatFs stat = new StatFs(path.getPath());// A class that simulates the df command of Linux to get the usage of SD card and mobile phone memory
        long blockSize = stat.getBlockSize();// returns Int, the size, in bytes, a file system
        long availableBlocks = stat.getAvailableBlocks();// Return Int to get the currently available storage space
        return availableBlocks * blockSize;
    }

    /**
     * Get the amount of free space outside the phone
     */
    public static long getAvailableExternalMemorySize() {
        if (isSDCardPresent()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            throw new RuntimeException("Don't have sdcard.");
        }
    }

    /**
     * Get the size of the phone's internal space
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();// Get the number of file systems available in the area
        return totalBlocks * blockSize;
    }

    /**
     * Get the size of the phone's external space
     */
    public static long getTotalExternalMemorySize() {
        if (isSDCardPresent()) {
            File path = Environment.getExternalStorageDirectory();// Get the external storage directory i.e. SDCard
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            throw new RuntimeException("Don't have sdcard.");
        }
    }
}
