package com.library.utilities;

import android.content.Intent;
import android.net.Uri;

public class CallUtils {

    private CallUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static Intent call(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        if (VersionUtils.hasLollipopOrHigher()) {
            callIntent.setPackage("com.android.server.telecom");
        } else {
            callIntent.setPackage("com.android.phone");
        }
        callIntent.setData(Uri.parse("tel:"+Uri.encode(phoneNumber.trim())));
        return callIntent;
    }

    public static Intent dial(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+Uri.encode(phoneNumber.trim())));
        return callIntent;
    }
}