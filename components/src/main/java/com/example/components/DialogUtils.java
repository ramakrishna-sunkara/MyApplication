package com.example.components;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


public class DialogUtils{
    public static void showDialog(@NonNull Context context, @NonNull String message)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setNeutralButton(R.string.ok, null);
        alertDialog.show();
    }

    public static void showDialog(@NonNull Context context, @NonNull String message, @NonNull String title)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setNeutralButton(R.string.ok, null);
        alertDialog.show();
    }

    public static void showDialog(@NonNull Context context, @NonNull String message, String title, @NonNull String positiveBtnTxt, @NonNull String negativeBtnTxt, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        if (title != null && !title.isEmpty()) {
            alertDialog.setTitle(title);
        }
        alertDialog.setPositiveButton(positiveBtnTxt, positiveListener);
        alertDialog.setNegativeButton(negativeBtnTxt, negativeListener);
        alertDialog.show();
    }
}
