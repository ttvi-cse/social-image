package com.hcmut.social.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.hcmut.social.R;

/**
 * Created by John on 10/6/2016.
 */

public class DialogUtil {
    public static Dialog createProgressDialog(Context ctx) {
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage(ctx.getString(R.string.loading));

        return progressDialog;
    }

    public static void showToastMessage(Context context, int resId) {
        showToastMessage(context,
                context.getString(resId));
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showYesNoDialog(Context ctx, DialogInterface.OnClickListener dialogClickListener, CharSequence question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(question).setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no,
                        dialogClickListener).show();
    }

    public static void showYesNoDialog(Context ctx,
                                       DialogInterface.OnClickListener dialogYesClickListener,
                                       DialogInterface.OnClickListener dialogNoClickListener,
                                       CharSequence question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(question).setPositiveButton(R.string.yes, dialogYesClickListener)
                .setNegativeButton(R.string.no,
                        dialogNoClickListener).show();
    }

    public static void showChooseDialog(Context ctx,
                                        DialogInterface.OnClickListener dialogClickListener,
                                        DialogInterface.OnCancelListener dialogCancelListener,
                                        CharSequence question, String option1, String option2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(question).setPositiveButton(option2, dialogClickListener)
                .setNegativeButton(option1, dialogClickListener)
                .setOnCancelListener(dialogCancelListener).show();
    }

    public static AlertDialog showAlertDialog(Context ctx, String message) {
        return showAlertDialog(ctx,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {

                    }
                },
                message);
    }

    /**
     * Thong Nguyen 21/03/2015
     * @param ctx
     * @param dialogClickListener
     * @param message
     */
    public static AlertDialog showAlertDialog(Context ctx, DialogInterface.OnClickListener dialogClickListener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        AlertDialog dialog = builder.setMessage(message).setPositiveButton(R.string.ok, dialogClickListener).create();
        dialog.show();
        return dialog;
    }

    public static void showInfoLog(final String tag, String message) {
        Log.i(tag, message);
    }

    public static void showDebugLog(final String tag, String message) {
        Log.d(tag, message);
    }
}
