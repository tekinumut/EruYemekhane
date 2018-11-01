package com.Mtkn.umutt.eruyemekhane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String message ="Yükleniyor... "+millisUntilFinished/1000;
                textView.setText(message);
            }

            @Override
            public void onFinish() {
                b.dismiss();
            }
        }.start();

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
