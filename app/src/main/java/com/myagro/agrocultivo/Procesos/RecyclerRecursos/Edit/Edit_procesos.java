package com.myagro.agrocultivo.Procesos.RecyclerRecursos.Edit;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myagro.agrocultivo.Procesos.RecyclerRecursos.Grid.Grid_Terreno;
import com.myagro.agrocultivo.R;
import com.myagro.agrocultivo.RecyclreMisCultivos.Add_cultivo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit_procesos extends AppCompatActivity {


        public Edit_procesos(Context contexto, String recurso, String hora, String inversion, String nota, final String id){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_edit_procesos);
        dialog.show();

                TextView recur = (TextView) dialog.findViewById(R.id.txt_recurso);
                EditText txt_inversion = (EditText) dialog.findViewById(R.id.txt_inversion);
                EditText txt_horas = (EditText) dialog.findViewById(R.id.txt_horas);
                EditText txt_nota = (EditText) dialog.findViewById(R.id.txt_nota);

                recur.setText(recurso);
                txt_inversion.setText(inversion);
                txt_horas.setText(hora);
                txt_nota.setText(nota);

                final String inv = txt_inversion.getText().toString();
                final String hor = txt_horas.getText().toString();
                final String not = txt_nota.getText().toString();
                final String id_id = id;


                Button delete = (Button) dialog.findViewById(R.id.btn_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { dialog.dismiss(); }
                });

                Button aceptar = (Button)dialog.findViewById(R.id.btn_aceptar);
                aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                        }
                });



    }


}






