package com.library.imagecompressor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import java.io.File;

public class Compressor {

    @SuppressLint("StaticFieldLeak")
    private static volatile Compressor INSTANCE;
    private final Context context;

    /**
     * The maximum width, the default is 720
     */
    private float maxWidth = 612.0f;

    /**
     * The maximum height, the default is 960
     */
    private float maxHeight = 816.0f;

    /**
     * The default compression method is JPEG, which is lossy compression.
     * PNG loss less image format, when calling bitmap.compress(CompressFormat format, int quality, OutputStream stream)
     * If CompressFormat.PNG is passed in, the picture will not be compressed, on the contrary, the saved picture may be larger than the original picture
     * CompressFormat.WEBP is the image format recommended by Google. If this format is passed in, the image compression ratio will be greater, that is, the compressed image will be much smaller than the original image.
     * For higher requirements, this method is recommended, because the compressed image is small and not very blurry
     */
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;

    /**
     * The default image processing method is ARGB_8888, which is a 32-bit image, and one pixel occupies 4 bytes
     * If a 2560 x 1440 image is loaded into the memory using the ARGB_8888 format (default), the occupied memory is
     * 2560 x 1440 x 4 = 14745600 (bytes) = 14.0625 MB so the big picture is loaded into the memory and easy to oom
     */
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    /**
     * The default compression quality is 80, and the compression quality is between 70-80. The quality compression effect is more obvious, and the lower the image size is not obvious, but the image becomes blurred
     */
    private int quality = 80;

    /**
     * Storage path
     */
    private String destinationPathWhereCompressedFileSave;

    /**
     * File name prefix
     */
    private String fileNamePrefix;

    /**
     * file name
     */
    private String fileName;

    private Compressor(Context context) throws CompressorFolderException {
        this.context = context;

        /**
         * Default destination
         */
        File compressorDir = context.getExternalFilesDir("Compressor");

        if (!compressorDir.exists()) {
            if (!compressorDir.mkdirs()) {
                throw new CompressorFolderException("Oops! Compressor directory create failed....");
            } else {
                System.out.println("Directory create successful....");
            }
        } else {
            System.out.println("Directory already exit....");
        }

        /**
         * directory where compress files store
         *
         * /storage/emulated/0/Android/data/com.bidfrail.android/files/Compressor/IMG_20221110_164207.jpeg
         */
        this.destinationPathWhereCompressedFileSave = compressorDir.getAbsolutePath();
    }

    public static Compressor getDefault(Context context) throws CompressorFolderException {
        if (INSTANCE == null) {
            synchronized (Compressor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Compressor(context);
                }
            }
        }
        return INSTANCE;
    }

    public File compressToFile(File file) {
        return CompressorUtils.compressImage(
                context,
                Uri.fromFile(file),
                maxWidth,
                maxHeight,
                compressFormat,
                bitmapConfig,
                quality,
                destinationPathWhereCompressedFileSave,
                fileNamePrefix,
                fileName
        );
    }

    public Bitmap compressToBitmap(File file) {
        return CompressorUtils.getScaledBitmap(
                context,
                Uri.fromFile(file),
                maxWidth,
                maxHeight,
                bitmapConfig
        );
    }

    public static class Builder {
        private Compressor compressor;

        public Builder(Context context) throws CompressorFolderException {
            compressor = new Compressor(context);
        }

        public Builder setMaxWidth(float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressor.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationPathWhereCompressedFileSave = destinationDirectoryPath;
            return this;
        }

        public Builder setFileNamePrefix(String prefix) {
            compressor.fileNamePrefix = prefix;
            return this;
        }

        public Builder setFileName(String fileName) {
            compressor.fileName = fileName;
            return this;
        }

        public Compressor build() {
            return compressor;
        }
    }
}
