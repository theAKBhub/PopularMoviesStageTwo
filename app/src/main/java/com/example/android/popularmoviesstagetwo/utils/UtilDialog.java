package com.example.android.popularmoviesstagetwo.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.popularmoviesstagetwo.R;

public class UtilDialog {

    public static boolean sIsDialogVisible;
    public static Dialog sDialog;

    public static boolean showDialog(String dialogMessage, Context context) {
        TextView textViewDialogTitle;
        final TextView textViewDialogCaption;
        Button buttonDialog;

        sDialog = new Dialog(context);
        sDialog.setContentView(R.layout.dialog);
        textViewDialogTitle = sDialog.findViewById(R.id.text_dialog_title);
        textViewDialogCaption = sDialog.findViewById(R.id.text_dialog_caption);
        buttonDialog = sDialog.findViewById(R.id.button_dismiss_dialog);

        Utils.setCustomTypeface(context, textViewDialogCaption);
        Utils.setCustomTypeface(context, textViewDialogTitle);
        Utils.setCustomTypeface(context, buttonDialog);

        textViewDialogCaption.setText(dialogMessage);

        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDialogCaption.setText("");
                sDialog.dismiss();
                sIsDialogVisible = false;
            }
        });
        sDialog.show();
        sIsDialogVisible = true;
        return sIsDialogVisible;
    }

    public static void dismissDialog() {
        sDialog.dismiss();
    }

}
