package com.contactlistapplication.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


import com.contactlistapplication.R;


public class CustomDialogManager {

    public static void showOkDialog(final Context context, String message) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog okCancelDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ok, null);
        build.setView(view);
        okCancelDialog = build.create();
        okCancelDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = view.findViewById(R.id.tvTitle);
        final TextView tvMessage = view.findViewById(R.id.tvMessage);
        final TextView btnYes =  view.findViewById(R.id.btnYes);
        btnYes.setText(context.getString(R.string.ok));
        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
            }
        });


        tvTitle.setText(R.string.app_name);

        if (TextUtils.isEmpty(message))
            tvMessage.setText("");

        else
            tvMessage.setText(message);

        okCancelDialog.show();
        okCancelDialog.setCanceledOnTouchOutside(false);
    }


    public static Dialog showProgressDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.vw_custom_progress_bar);
        WebView imageView=(WebView) dialog.findViewById(R.id.wvLoader);
        imageView.setBackgroundColor(Color.TRANSPARENT); //for gif without background
        imageView.loadUrl("file:///android_asset/html/loader.html");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }


    public static void showShortToast(CharSequence msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }




}