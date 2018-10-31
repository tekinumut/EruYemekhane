package com.Mtkn.umutt.eruyemekhane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Mtkn.umutt.eruyemekhane.R;

class ShowDialog {

    final private Context mContext;
    private AlertDialog b;

    ShowDialog(Context mContext) {
        this.mContext = mContext;
    }

    void ShowProgressDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        @SuppressLint("InflateParams")
        View dialogView= inflater.inflate(R.layout.progress_dialog_layout, null);

        TextView textView = dialogView.findViewById(R.id.textViewProgress);
        String message = "Yükleniyor";
        textView.setText(message);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        b = dialogBuilder.create();
        b.show();
    }

    void HideProgressDialog(){

        if(b!=null)
        b.dismiss();
    }

}
