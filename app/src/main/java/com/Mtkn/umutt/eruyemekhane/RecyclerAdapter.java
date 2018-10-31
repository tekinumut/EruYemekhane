package com.Mtkn.umutt.eruyemekhane;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private final List<RecyclerModel> recyclerList;

    public RecyclerAdapter(Context mContext, List<RecyclerModel> recyclerList) {
        this.mContext = mContext;
        this.recyclerList = recyclerList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View mView =View.inflate(mContext,R.layout.rec_inside_tab1_2,null);
        return new ViewHolder(mView,mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RecyclerModel model =recyclerList.get(position);

        holder.tarih.setText(model.getTarih());
        holder.yemek.setText(model.getYemekler());
        holder.topCal.setText(mContext.getString(R.string.topCalTextView,model.getTopCal())); //SetText'in gariplikleri işte. İlgili detay linkte:
        //https://stackoverflow.com/questions/33164886/android-textview-do-not-concatenate-text-displayed-with-settext

    }

    @Override
    public int getItemCount() {
        return recyclerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView tarih;
        final TextView yemek;
        final TextView topCal;
        ViewHolder(View itemView,Context context) {
            super(itemView);
            mContext=context;

             tarih=itemView.findViewById(R.id.rec_tarih);
             yemek=itemView.findViewById(R.id.rec_yemek);
             topCal=itemView.findViewById(R.id.rec_topCal);
        }
    }//end of ViewHolder

}
