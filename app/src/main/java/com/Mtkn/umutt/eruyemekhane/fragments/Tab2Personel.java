package com.Mtkn.umutt.eruyemekhane.fragments;

import androidx.room.Room;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Mtkn.umutt.eruyemekhane.abstracts.ConnectivityStatus;
import com.Mtkn.umutt.eruyemekhane.GetValuesWithAsync;
import com.Mtkn.umutt.eruyemekhane.R;
import com.Mtkn.umutt.eruyemekhane.RecyclerAdapter;
import com.Mtkn.umutt.eruyemekhane.RoomFiles.ValuesDatabase;
import com.Mtkn.umutt.eruyemekhane.MainActivity;

public class Tab2Personel extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.tab1_tab2_of_tablayout,container,false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        refreshLayout=rootView.findViewById(R.id.recycler_refresh);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL)); //Recyclerview için her bloğu ayırır.

        ValuesDatabase database= Room.databaseBuilder(mContext,ValuesDatabase.class,mContext.getString(R.string.my_values))
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()//database güncellenirse verileri sil
                 .build();

        if(!ConnectivityStatus.isConnected(mContext))
        {
            recyclerView.setAdapter(new RecyclerAdapter(mContext,database.valuesDAO().getValues("2")));
        }

    new GetValuesWithAsync(this,false).execute(".ListeSatir >.YemekTarih",".ListeSatir >.YemekListe >ul","2");

        return rootView;
    }


    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(this::loadValues,500);
    }

    private void loadValues()
    {
        if(ConnectivityStatus.isConnected(mContext))
        {
     new GetValuesWithAsync(this,false).execute(".ListeSatir >.YemekTarih",".ListeSatir >.YemekListe >ul","2");
            Snackbar.make(((MainActivity)mContext).findViewById(R.id.coordinatorLayout),
                    "Kayıtlarınız yenilendi.",1500).show();
        }
        else if (!ConnectivityStatus.isConnected(mContext))
        {
            Snackbar snackbar=Snackbar.make(((MainActivity)mContext).findViewById(R.id.coordinatorLayout),
                    "İnternete bağlanılamadı. Verileriniz güncel olmayabilir.",3000);
            snackbar.show();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
