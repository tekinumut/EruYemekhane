package com.Mtkn.umutt.eruyemekhane;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.Mtkn.umutt.eruyemekhane.abstracts.ConnectivityStatus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetValuesWithAsync extends AsyncTask<String, String, String[]> {
    private final WeakReference<Fragment> weakReference;
    private Document document;
    private ShowDialog showDialog;
    final private boolean isShow;
    public GetValuesWithAsync(Fragment fragment, boolean isShow) {
        weakReference= new WeakReference<>(fragment);
        this.isShow =isShow;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(isShow)
        {
            showDialog=new ShowDialog(mContext());
            showDialog.ShowProgressDialog();
        }
    }
    @Override
    protected String[] doInBackground(String... strings) {
        try {
            document =Jsoup.connect("https://www.erciyes.edu.tr/kategori/KAMPUSTE-YASAM/Yemek-Hizmetleri/22/167").timeout(14500).get();
        } catch (IOException e) {
            Snackbar.make(((MainActivity)mContext()).findViewById(R.id.coordinatorLayout),
                    "Bağlantı sağlanamadı. İnternetinizde veya sitede bir sorun olabilir.",60000)
                    .setAction("Tamam",v ->{} ).setActionTextColor(Color.parseColor("#2980b9"))
                    .show(); //Boş setAction , butona tıklandığında snackbarı kapatacaktır.
        }

        return strings;
    }
    @Override
    protected void onPostExecute(String[] values) {
        super.onPostExecute(values);
        Fragment fragment=weakReference.get();
        if(fragment ==null || fragment.isRemoving())
            return ;

        RecyclerView recyclerView=Objects.requireNonNull(fragment.getView()).findViewById(R.id.recycler_view);
        ValuesDatabase database= Room.databaseBuilder(mContext(),ValuesDatabase.class,mContext().getString(R.string.my_values))
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()//database güncellenirse verileri sil
                .build();

        if(document!=null && ConnectivityStatus.isConnected(mContext())) //Eğer internet varsa
        {
        List<RecyclerModel> recyclerList = new ArrayList<>();
        Elements ogrTarihValue =document.select(values[0]);
        //Yukarıda öğrenciler için aktif olan listelerin tarih bilgisi alınır.

        RecyclerModel recyclerModel = null;
        for (int i = 0; i < ogrTarihValue.size(); i++) { //tüm öğle tarihlerini alır. daha sonra bu tarihlere ait yemek listesi alınacak
            StringBuilder builder = new StringBuilder(); //Tüm yemekleri satır satır builder'a ekleyeceğiz.
            int topCal =0; //Yemek bölümünün sonuna toplam kaloriyi yazacağız.
            ArrayList<String> arrayTopCal =new ArrayList<>(); //Kalori değerlerini alıp bunların toplanmasını sağlayacak list.
            Elements ogrYemekValue =document.select(values[1]).get(i).select("li");
            //Yukarıda get(i) ile her günün yemeklerinin listesi alınır. select("li") yapıyorsun çünkü o yemeğe ait li'yi alman lazım.
            for (int j = 0; j <ogrYemekValue.size() ; j++) {//O günün tüm yemeklerini tek tek alana kadar çalış.
                builder.append("\u2022  ")
                .append(ogrYemekValue.get(j).select("li").text())
                .append("\n"); //Yemekler satır satır yazılır ve diğer günün yemekleri alınmak üzere döngüden çıkılır.

    try{//Toplam kalori değerini yazdır.
        arrayTopCal.add(ogrYemekValue.get(j).select("li").text().replaceAll("\\D","")); //Her satırdaki sadece sayısal değerler alınır.
        topCal+=Integer.valueOf(arrayTopCal.get(j));}   catch (Exception ignored){} //Bun sayılar tek tek toplanarak topCal değerine yazılır.
                String topCalString=String.valueOf(topCal);
    if(topCal==0)
        topCalString="Kalori belirtilmedi.";
                if(j==ogrYemekValue.size()-1) //Eğer döngünün sonundaysam
                {
        builder.delete(builder.lastIndexOf("\n"),builder.length());//En sonda \n ile bırakılan boşluğu sil. Çünkü yazı bir boşluk fazla gözüküyor.

        recyclerModel =new RecyclerModel(ogrTarihValue.get(i).text(),builder.toString(),topCalString,values[2]);
                }
            }
            recyclerList.add(recyclerModel);//her gün için verileri liste ekle. fori içerisinde
        }

       if(recyclerList.size()==0)//Eğer hiç veri yoksa snackbar ile kullanıcıyı bilgilendir.
       {
           Snackbar.make(((MainActivity)mContext()).findViewById(R.id.coordinatorLayout),
                   "Herhangi bir kayıt bulunamadı. Bunun sebebi listenin henüz yayınlanmamış olması olabilir.",60000)
                   .setAction("Tamam",v ->{} ).setActionTextColor(Color.parseColor("#2980b9"))
                   .show(); //Boş setAction , butona tıklandığında snackbarı kapatacaktır.

       }
        database.valuesDAO().deleteTable(values[2]);
        database.valuesDAO().addValues(recyclerList);
        recyclerView.setAdapter(new RecyclerAdapter(mContext(),database.valuesDAO().getValues(values[2])));

        }
        if(isShow)
            showDialog.HideProgressDialog();
        }//End of onPost

    private Context mContext()
    {
        return weakReference.get().getContext();
    }
}//end of class

