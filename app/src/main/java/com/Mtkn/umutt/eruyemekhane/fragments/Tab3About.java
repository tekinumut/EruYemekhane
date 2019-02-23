package com.Mtkn.umutt.eruyemekhane.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Mtkn.umutt.eruyemekhane.R;

public class Tab3About extends Fragment {

    TextView kullanim_talimati_text,geriBildirim,appDegerlendir,gitHubKodlari;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.tab3_about,container,false);

        kullanim_talimati_text=rootView.findViewById(R.id.text_kullanim_talimati);
        geriBildirim=rootView.findViewById(R.id.geriBildirim);
        appDegerlendir=rootView.findViewById(R.id.appDegerlendir);
        gitHubKodlari=rootView.findViewById(R.id.gitHubKodlari);

        String kullanim_talimati =" \u2022 Eğer liste boş gözüküyorsa bunun sebebi yemek listesinin henüz sitede yayımlanmamış olmasıdır.\n" +
                " \u2022 Uygulamaya çevrimdışı girerseniz, son yüklediğiniz kayıtlar gözükecektir. " +
                "Ana sayfada iken ekranı yukarıdan aşağı çekerek listeleri yenileyebilirsiniz.\n" +
                " \u2022 Geliştiriciler aşağıdaki bölümden uygulamanın kaynak kodlarına erişebilir.\n";

        kullanim_talimati_text.setText(kullanim_talimati);

        geriBildirim.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mtkn358@gmail.com"});
            startActivity(Intent.createChooser(intent, "Mail gönder"));
        });
        appDegerlendir.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + rootView.getContext().getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + rootView.getContext().getPackageName())));
            }
        });
        gitHubKodlari.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/tekinumut/EruYemekhane"))));
        return rootView;
    }
}
