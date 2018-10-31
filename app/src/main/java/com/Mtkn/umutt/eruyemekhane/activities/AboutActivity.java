package com.Mtkn.umutt.eruyemekhane.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Mtkn.umutt.eruyemekhane.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView kullanim_talimati_text=findViewById(R.id.text_kullanim_talimati);

        String kullanim_talimati =" \u2022 Eğer liste boş gözüküyorsa bunun sebebi yemek listesinin henüz sitede yayımlanmamış olmasıdır.\n" +
" \u2022 Uygulamaya çevrimdışı girerseniz, son yüklediğiniz kayıtlar gözükecektir. " +
"Ana sayfada iken ekranı yukarıdan aşağı çekerek listeleri yenileyebilirsiniz.\n" +
" \u2022 Ayarlar bölümünden isteğiniz doğrultusunda öğle-akşam menülerini gizleyebilirsiniz. \n" +
" \u2022 Geliştiriciler aşağıdaki bölümden uygulamanın kaynak kodlarına erişebilir.\n";

        kullanim_talimati_text.setText(kullanim_talimati);

    }

    public void geri_bildirim_gonder(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mtkn358@gmail.com"});
        startActivity(Intent.createChooser(intent, "Mail gönder"));
    }

    public void uygulamayi_degerlendir(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }

    }

    public void github_eris(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/tekinumut/EruYemekhane")));

    }
}
