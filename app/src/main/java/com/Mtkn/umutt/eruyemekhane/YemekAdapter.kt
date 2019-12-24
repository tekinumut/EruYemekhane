package com.Mtkn.umutt.eruyemekhane

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class YemekAdapter(private val yemekList: List<YemekModel>) :
    RecyclerView.Adapter<YemekAdapter.YemekViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {

        return YemekViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rec_inside_tab_1_2, parent, false))
    }

    override fun getItemCount(): Int = yemekList.size

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        val yemekModel = yemekList[position]

        holder.recTarih?.text = yemekModel.tarih
        holder.recYemekList?.text = yemekModel.yemekler
    }

    class YemekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var recTarih: TextView? = itemView.findViewById(R.id.rec_tarih)
        var recYemekList: TextView? = itemView.findViewById(R.id.rec_yemek_list)


    }
}