package com.library.utilities;

import android.content.Intent;
import android.os.Build;

import androidx.core.app.ShareCompat;
import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;

import java.io.File;

public class ImplicitIntentUtils {

    private ImplicitIntentUtils() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    public static Intent actionGetContentIntent(String mimeType, boolean showCloudStorage) {
        Intent intent = new Intent();
        intent.setType(mimeType);

        if (Build.VERSION.SDK_INT < 19)
        {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent = Intent.createChooser(intent, "Select a file");
        }
        else
        {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, !showCloudStorage);
        }
        return intent;
    }

    public static Intent actionPickIntent(int mediaType) {
        Intent intent = new Intent(Intent.ACTION_PICK);

        if (mediaType == 1)
        {
            intent.setType("image/*");

            if (Build.VERSION.SDK_INT < 19)
            {
                intent = Intent.createChooser(intent, "Select Image");
            }
            else
            {
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return intent;
        }
        else if (mediaType == 2)
        {
            intent.setType("video/*");

            if (Build.VERSION.SDK_INT < 19)
            {
                intent = Intent.createChooser(intent, "Select Video");
            }
            else
            {
                String[] mimeTypes = {"video/mp4"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return intent;
        }
        return null;
    }

    /**
     * Open app page in playstore.
     *
     * @param context the context
     */
    public static void openAppPageInPlaystore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Open url in browser.
     *
     * @param context the context
     * @param url     the url
     */
    public static void openUrlInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Send email.
     *
     * @param context the context
     * @param sendTo  the send to
     * @param subject the subject
     * @param body    the body
     */
    public static void sendEmail(Context context, String[] sendTo, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, sendTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, ""));
        }
    }


    /**
     * Share my app.
     *
     * @param context the context
     * @param subject the subject
     * @param message the message
     */
    public static void shareMyApp(Context context, String subject, String message) {
        try {
            String appUrl = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            String leadingText = "\n" + message + "\n\n";
            leadingText += appUrl + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, leadingText);
            context.startActivity(Intent.createChooser(i, "Share using"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rate my app.
     *
     * @param context the context
     */
    public static void rateMyApp(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()));
            if (i.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(i);
            } else {
                Toast.makeText(context, "Playstore Unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Call.
     *
     * @param context the context
     * @param number  the number
     */
    @RequiresPermission(permission.CALL_PHONE)
    public static void call(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, return
            return;
        }

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Send sms.
     *
     * @param context      the context
     * @param sendToNumber the send to number
     * @param message      the message
     */
    public static void sendSMS(Context context, String sendToNumber, String message) {
        Uri smsUri = Uri.parse("tel:" + sendToNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.putExtra("address", sendToNumber);
        intent.putExtra("sms_body", message);
        intent.setType("vnd.android-dir/mms-sms");

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Show location in map.
     *
     * @param context   the context
     * @param latitude  the latitude
     * @param longitude the longitude
     * @param zoomLevel the zoom level
     */
    public static void showLocationInMap(Context context, String latitude, String longitude, String zoomLevel) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = String.format("geo:%s,%s", latitude, longitude);
        if (zoomLevel != null) {
            data = String.format("%s?z=%s", data, zoomLevel);
        }
        intent.setData(Uri.parse(data));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Show location in map.
     *
     * @param context      the context
     * @param locationName the location name
     */
    public static void showLocationInMap(Context context, String locationName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + locationName));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Share data.
     *
     * @param context  the context
     * @param dir      the dir
     * @param fileName the file name
     * @param type     the type
     * @param data     the data
     */
    public static void shareData(Context context, String dir, String fileName, String type, String data) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(type);
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + dir, fileName));
        intent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        intent.putExtra("data", data);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Share using"));
        }
    }

    /**
     * Share text.
     *
     * @param activity the activity
     * @param title    the title
     * @param textData the text data
     */
    public static void shareText(Activity activity, String title, String textData) {
        ShareCompat.IntentBuilder.from(activity).setType("text/plain").setChooserTitle(title)
                .setText(textData).startChooser();
    }

    /**
     * Take a pic.
     *
     * @param context  the context
     * @param dir      the dir
     * @param fileName the file name
     */
    void takeAPic(Context context, String dir, String fileName) {
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + dir, fileName));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
