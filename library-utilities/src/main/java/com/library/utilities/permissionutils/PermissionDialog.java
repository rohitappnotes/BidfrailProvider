package com.library.utilities.permissionutils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.library.utilities.listener.AlertDialogListener;

public class PermissionDialog {

    private PermissionDialog() {
        throw new UnsupportedOperationException("Should not create instance of PermissionDialog class. Please use as static..");
    }

    public static void permissionDeniedWithNeverAskAgain(final Activity activity, int permissionIcon, String permissionName, String neverAskAgainMessage, final String permission, final ActivityResultLauncher<Intent> intentActivityResultLauncher, AlertDialogListener alertDialogListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(permissionIcon);
        alertDialogBuilder.setTitle(permissionName);
        alertDialogBuilder.setMessage(neverAskAgainMessage);

        alertDialogBuilder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                alertDialogListener.onCancel();
            }
        });

        alertDialogBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                alertDialogListener.onSettings();
                Intent intent = PermissionSettingIntent.getIntent(activity, permission);
                intentActivityResultLauncher.launch(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button positiveButton   = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton   = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        positiveButton.setBackgroundColor(Color.parseColor("#000000"));

        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20, 0, 0, 0);
        positiveButton.setLayoutParams(params);
    }

    public static void permissionDeniedWithNeverAskAgain(final Activity activity, final Fragment fragment, int permissionIcon, String permissionName, String neverAskAgainMessage, final String permission, final ActivityResultLauncher<Intent> intentActivityResultLauncher, AlertDialogListener alertDialogListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(permissionIcon);
        alertDialogBuilder.setTitle(permissionName);
        alertDialogBuilder.setMessage(neverAskAgainMessage);


        alertDialogBuilder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                alertDialogListener.onCancel();
            }
        });

        alertDialogBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                alertDialogListener.onSettings();
                Intent intent = PermissionSettingIntent.getIntent(activity, permission);
                intentActivityResultLauncher.launch(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button positiveButton   = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton   = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        positiveButton.setBackgroundColor(Color.parseColor("#000000"));

        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20, 0, 0, 0);
        positiveButton.setLayoutParams(params);
    }
}