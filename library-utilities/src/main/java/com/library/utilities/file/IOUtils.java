package com.library.utilities.file;

import android.database.Cursor;
import android.os.ParcelFileDescriptor;
import java.io.Closeable;

public class IOUtils {

    private IOUtils() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    public static void close(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Fail to close : " + throwable);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Fail to close : " + throwable);
        }
    }

    /* ParcelFileDescriptor did not implement Closable before API level 16. */
    public static void close(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            return;
        }
        try {
            parcelFileDescriptor.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Fail to close : " + throwable);
        }
    }

    public static void close(Cursor cursor) {
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Fail to close : " + throwable);
        }
    }
}
