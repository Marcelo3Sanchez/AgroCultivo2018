package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Edit;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myagro.agrocultivo.R;

public class Edit_venta extends AppCompatActivity{

        public Edit_venta(Context contexto, String recurso, String hora, String inversion, String nota){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_edit_venta);
        dialog.show();

                TextView recur = (TextView) dialog.findViewById(R.id.txt_recurso_vent);
                EditText txt_inversion = (EditText) dialog.findViewById(R.id.txt_inversion_vent);
                EditText txt_horas = (EditText) dialog.findViewById(R.id.txt_horas_vent);
                EditText txt_nota = (EditText) dialog.findViewById(R.id.txt_nota_vent);

                recur.setText(recurso);
                txt_inversion.setText(inversion);
                txt_horas.setText(hora);
                txt_nota.setText(nota);

                Button delete = (Button) dialog.findViewById(R.id.btn_delete_vent);
                delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { dialog.dismiss(); }
                });

                Button aceptar = (Button)dialog.findViewById(R.id.btn_aceptar_vent);
                aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                dialog.dismiss();

                        }
                });
    }


}
