package com.myagro.agrocultivo.RecyclreMisCultivos;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myagro.agrocultivo.Procesos.CultProcesos;
import com.myagro.agrocultivo.R;

import java.util.List;

public class RecyclerViewAdapter_cultivos extends RecyclerView.Adapter<RecyclerViewAdapter_cultivos.MyViewHolder> {


    private Context mContext;
    private List<CardCultivos> mData;

    public RecyclerViewAdapter_cultivos(Context mContext, List<CardCultivos> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_carview_cultivos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_id_cult.setText(mData.get(position).getId_cult());
        holder.tv_name_cult.setText(mData.get(position).getName_cult());
        holder.img_cult_thumbnail.setImageResource(mData.get(position).getThumbnail());

        //Click Listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent (mContext, CultProcesos.class);
                //enviar id del cultivo a Procesos class
                intent.putExtra("idCultivo",mData.get(position).getId_cult());
                intent.putExtra("name_cult",mData.get(position).getName_cult());
                intent.putExtra("tipo_arroz",mData.get(position).getTipo_arroz());
                intent.putExtra("tipo_siembra",mData.get(position).getTipo_siembra());
                intent.putExtra("created_ad",mData.get(position).getCreated_ad());
                mContext.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id_cult;
        TextView tv_name_cult;
        ImageView img_cult_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id_cult = (TextView)itemView.findViewById(R.id.id_cultivo);
            tv_name_cult = (TextView)itemView.findViewById(R.id.name_cultivo_id);
            img_cult_thumbnail = (ImageView)itemView.findViewById(R.id.imag_cultivo_id);
            cardView = (CardView)itemView.findViewById(R.id.cultivos_cardview_id);
        }
    }


}
