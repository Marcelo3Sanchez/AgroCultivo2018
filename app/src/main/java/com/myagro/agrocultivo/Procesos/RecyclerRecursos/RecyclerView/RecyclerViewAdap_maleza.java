package com.myagro.agrocultivo.Procesos.RecyclerRecursos.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Card_recursos;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Edit.Edit_maleza;
import com.myagro.agrocultivo.R;

import java.util.List;

public class RecyclerViewAdap_maleza extends RecyclerView.Adapter<RecyclerViewAdap_maleza.MyViewHolder> {


    private Context contextos;
    private Context mContext;
    private List<Card_recursos> mData;
    String recurso,cant,inversion,nota;



    public RecyclerViewAdap_maleza(Context contextos,Context mContext, List<Card_recursos> mData) {
        this.contextos = contextos;
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_carview_recursos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_id_recurs.setText(mData.get(position).getId());
        holder.tv_tipo_recurs.setText(mData.get(position).getTipo_recurso());
        holder.tv_cantidad_recurs.setText(mData.get(position).getN_horas()+" L");
        holder.tv_val_inver_recurs.setText("Inversion: $"+mData.get(position).getVal_inversion());
        holder.tv_fecha_recurs.setText(mData.get(position).getCreated_at());

        holder.cardView_recurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recurso = mData.get(position).getTipo_recurso();
                cant = mData.get(position).getN_horas();
                inversion = mData.get(position).getVal_inversion();
                nota = mData.get(position).getNotas();
                new Edit_maleza(contextos,recurso,cant,inversion,nota);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id_recurs;
        TextView tv_tipo_recurs;
        TextView tv_cantidad_recurs;
        TextView tv_val_inver_recurs;
        TextView tv_fecha_recurs;
        CardView cardView_recurs;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_id_recurs = (TextView)itemView.findViewById(R.id.id_recurs);
            tv_tipo_recurs = (TextView)itemView.findViewById(R.id.tipo_recurso_recurs);
            tv_cantidad_recurs = (TextView)itemView.findViewById(R.id.n_horas_recurs);
            tv_val_inver_recurs = (TextView)itemView.findViewById(R.id.valor_recurs);
            tv_fecha_recurs = (TextView)itemView.findViewById(R.id.fecha_recurs);
            cardView_recurs = (CardView)itemView.findViewById(R.id.recurs_cardview_id);
        }
    }


}
