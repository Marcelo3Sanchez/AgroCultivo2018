package com.myagro.agrocultivo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myagro.agrocultivo.RecyclreMisCultivos.Add_cultivo;
import com.myagro.agrocultivo.RecyclreMisCultivos.MisCultivosGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {

    private Context contextos;
    EditText tokentext;

    ArrayList<BarData> list;

    public int cerrarsesion(Context contexto){

        final Dialog dialog = new Dialog(contexto);
        dialog.requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_cerrar_sesion);
        dialog.show();

        Button cerrar = (Button) dialog.findViewById(R.id.btn_cerrar_sesion);
        Button cancelar = (Button) dialog.findViewById(R.id.btn_cerar_cancelar);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tk = "No existe token";
                put_token(tk);
                SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id",null);
                editor.putString("user",null);
                editor.putString("pass",null);
                editor.putString("idCultivo", null);
                editor.commit();
                dialog.dismiss();
                startActivity(new Intent(getBaseContext(), com.myagro.agrocultivo.Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                Toast.makeText(getApplicationContext(), "Sesion finalizada", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });
        return 0;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                case R.id.navigation_miscultivos:
                    Intent home = new Intent(Home.this, MisCultivosGrid.class);
                    startActivity(home);
                    finish();
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        contextos = this;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Obtener id del usuario
        SharedPreferences prefUser = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String id_user = prefUser.getString("id",null);

        //obtener el token
        SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        String tk = preferences.getString("id",null);
        put_token(tk);
        // Toast.makeText(getApplicationContext(), "Token  " + tk, Toast.LENGTH_LONG).show();

        //tokentext=(EditText) findViewById(R.id.token_text);
        //tokentext.setText(tk);

        ListView lv = (ListView)findViewById(R.id.listview_home);
        list = new ArrayList<>();
        obtener_barrasWS(id_user);
/*
        // for (int i=0; i<10 ; i++){
            list.add(generatedata(120.25,50.48,150.45,"Marcelo"));
            list.add(generatedata(80.25,20.48,160.45,"Quito"));
            list.add(generatedata(120.25,60.48,75.45,"Manabi"));
            list.add(generatedata(10.25,60.48,190.45,"Pedernales"));
            list.add(generatedata(25.25,80.48,150.45,"Aquisito"));
            list.add(generatedata(180.25,150.48,150.45,"Puedeser"));
            list.add(generatedata(250.25,500.48,700.45,"Bastante"));
            list.add(generatedata(360.25,1000.48,700.45,"No creo"));
            list.add(generatedata(500.25,2500.48,80.45,"Vacan"));
       // }
*/
        ChartDataAdapter charDataAdapter = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(charDataAdapter);
    }


    private class ChartDataAdapter extends ArrayAdapter<BarData>{

        public ChartDataAdapter(Context context, List<BarData> objects){
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            BarData data = getItem(position);
            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = getLayoutInflater().from(getContext()).inflate(R.layout.activity_list_item_barchar_home, null);
                holder.chart = (HorizontalBarChart) convertView.findViewById(R.id.barchar_home);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }



            data.setValueTextColor(Color.BLACK);

            holder.chart.getDescription().setEnabled(false);
            holder.chart.setDrawGridBackground(false);
            holder.chart.setDrawValueAboveBar(true);


            holder.chart.setFitBars(true);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);


            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setLabelCount(5, true);
            leftAxis.setSpaceTop(15f);

            YAxis ringAxis = holder.chart.getAxisRight();
            ringAxis.setLabelCount(5, true);
            ringAxis.setSpaceTop(5f);


            holder.chart.invalidate();


            holder.chart.setData(data);
            holder.chart.setFitBars(true);
            holder.chart.animateXY(300,500);

            return convertView;

        }


        private class ViewHolder{
            BarChart chart;
        }
    }


    private BarData generatedata (double ingreso, double gastos, double pronostico, String nombre){

        ArrayList<BarEntry> entries = new ArrayList<>();
        //for (int i=0; i<3 ; i++){

        entries.add(new BarEntry(3, (float)(gastos)));
        entries.add(new BarEntry(2, (float)(ingreso)));
        entries.add(new BarEntry(1, (float)(pronostico)));


       // }

        BarDataSet dataSet = new BarDataSet(entries,"  Cultivo: "+ nombre);
        dataSet.setValueTextSize(12);

        dataSet.setColors(
                ContextCompat.getColor(getBaseContext(), R.color.colorc),
                ContextCompat.getColor(getBaseContext(), R.color.colorb),
                ContextCompat.getColor(getBaseContext(), R.color.colord)
        );

       // dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setBarShadowColor(Color.rgb(203,203,203));

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        return data;
    }


    public void obtener_barrasWS(String id_user) {
        String url_optener_gastos = "http://206.189.165.63/dashboardgeneralbarra/"+id_user;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_optener_gastos, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(getApplication(), " "+response,Toast.LENGTH_SHORT).show();

                        JSONArray json = response.optJSONArray("barras");
                        JSONObject jsonObject = null;
                        try {
                            for (int i = 0; i < json.length(); i++) {
                                jsonObject = json.getJSONObject(i);
                                //JSONObject jornada = jsonArray.getJSONObject(i);
                                String namecult = jsonObject.getString("name_cult");
                                String egreso = jsonObject.getString("egresos");
                                String ingreso = jsonObject.getString("ingresos");
                                String capital_inicial = jsonObject.getString("capital_inicial");

                                double egresoD = Double.parseDouble(egreso);
                                double ingresoD = Double.parseDouble(ingreso);
                                //double ingresoD = 150.25;
                                double capitalD = Double.parseDouble(capital_inicial);

                                list.add(generatedata(ingresoD, egresoD, capitalD, namecult));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error "+error,Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueueGET = Volley.newRequestQueue(this);
        requestQueueGET.add(getRequest);
    }



    public  void put_token (final String tk){

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        final String iduser = preferences.getString("id",null);

        String url_envia_token = "http://206.189.165.63/login/logintoken/"+iduser;

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_envia_token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplication(), response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplication() , error+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String token= tk.toString();
                params.put("token",token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cerrar_sesion_menu_home: {
               cerrarsesion(contextos);
                break;
            }

            case R.id.dasboard_menu_home: {
                Intent home = new Intent(Home.this, MisCultivosGrid.class);
                startActivity(home);
                finish();
                break;
            }


        }
        return true;
    }
}



