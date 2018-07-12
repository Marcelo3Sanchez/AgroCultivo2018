package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Edit;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Add.Add_almacenamiento;
import com.myagro.agrocultivo.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit_Almacen extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

        String URL_POST_add_almacen = R.string.host+"add_cultivo.php";

        public Edit_Almacen(Context contexto, String recurso, String cantidad, String inversion, String nota){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_edit_alamcen);
        dialog.show();

                TextView recur = (TextView) dialog.findViewById(R.id.txt_recurso_edi_alm);
                EditText txt_inversion = (EditText) dialog.findViewById(R.id.txt_inversion_edi_alm);
                EditText txt_horas = (EditText) dialog.findViewById(R.id.txt_cant_edi_alm);
                EditText txt_nota = (EditText) dialog.findViewById(R.id.txt_nota_edi_alm);

                recur.setText(recurso);
                txt_inversion.setText(inversion);
                txt_horas.setText(cantidad);
                txt_nota.setText(nota);

                Button delete = (Button) dialog.findViewById(R.id.btn_delete_edi_alma);
                delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { dialog.dismiss(); }
                });

                Button aceptar = (Button)dialog.findViewById(R.id.btn_aceptar_edi_alm);
                aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                dialog.dismiss();

                        }
                });
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}


