package com.myagro.agrocultivo.Procesos.RecyclerRecursos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myagro.agrocultivo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Barchar_recursos extends AppCompatActivity {

    protected BarChart mChart;
    String cultiID;
    TextView txt_name_cult, txt_tiposiemb, txt_tipoarroz, txt_dimension_cult, txt_cant_semilla, txt_valor_inver,txt_capital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchar_recursos);

        Intent intent = getIntent();
        cultiID = intent.getExtras().getString("idcult");


        txt_name_cult = (TextView) findViewById(R.id.name_cult_info);
        txt_tiposiemb = (TextView) findViewById(R.id.tipo_siembra_info);
        txt_tipoarroz = (TextView) findViewById(R.id.tipo_arroz_info);
        txt_cant_semilla = (TextView) findViewById(R.id.cantidad_semilla_info);
        txt_dimension_cult = (TextView) findViewById(R.id.dimension_info);
        txt_valor_inver = (TextView) findViewById(R.id.val_inversion_info);
        txt_capital = (TextView) findViewById(R.id.capital_inicial_info);


        mChart = (BarChart) findViewById(R.id.barchar_recursos);
        mChart.getDescription().setEnabled(false);

        obtener_barrasWS(cultiID);
        //generatedata(10,20,30,40,50,60);
        mChart.setFitBars(true);

        obtener_info_cultivo(cultiID);

    }


    private void generatedata (double preterreno, double siembra, double fertrecurs, double ferinsumos, double cosecha, double almacen){

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(6, (float)(preterreno)));
        entries.add(new BarEntry(5, (float)(siembra)));
        entries.add(new BarEntry(4, (float)(fertrecurs)));
        entries.add(new BarEntry(3, (float)(ferinsumos)));
        entries.add(new BarEntry(2, (float)(cosecha)));
        entries.add(new BarEntry(1, (float)(almacen)));


        BarDataSet dataSet = new BarDataSet(entries," Gastos totales");
        dataSet.setValueTextSize(15);


        dataSet.setColors(

                ContextCompat.getColor(mChart.getContext(), R.color.colorb),
                ContextCompat.getColor(mChart.getContext(), R.color.colorc),
                ContextCompat.getColor(mChart.getContext(), R.color.colord),
                ContextCompat.getColor(mChart.getContext(), R.color.colore),
                ContextCompat.getColor(mChart.getContext(), R.color.colorf),
                ContextCompat.getColor(mChart.getContext(), R.color.colorg),
                ContextCompat.getColor(mChart.getContext(), R.color.colora),
                ContextCompat.getColor(mChart.getContext(), R.color.colorh)

        );



        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(5, true);
        leftAxis.setSpaceTop(15f);

        YAxis ringAxis = mChart.getAxisRight();
        ringAxis.setLabelCount(5, true);
        ringAxis.setSpaceTop(5f);


        BarData data = new BarData(dataSet);
        mChart.setData(data);
        mChart.invalidate();
        mChart.animateXY(500,500);

    }


    public void obtener_info_cultivo(String id_cultivo) {
        String url_optener_gastos = "http://206.189.165.63/miscultivos/cultivos/"+id_cultivo;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_optener_gastos, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(getApplication(), " "+response,Toast.LENGTH_SHORT).show();

                        JSONArray json = response.optJSONArray("cultivo");
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = json.getJSONObject(0);
                            String name_cultivo = jsonObject.getString("name_cult");
                            String dimension_cultivo = jsonObject.getString("dimension_cult");
                            String tipo_arroz = jsonObject.getString("tipo_arroz");
                            String tipo_siembra = jsonObject.getString("tipo_siembra");
                            String cantidad_semilla = jsonObject.getString("cantidad_semilla");
                            String valor_inversion = jsonObject.getString("valor_inversion");
                            String capital_inicial = jsonObject.getString("capital_inicial");

                            txt_name_cult.setText("Cultivo: "+name_cultivo);
                            txt_cant_semilla.setText(cantidad_semilla+" KG");
                            txt_capital.setText("$ "+capital_inicial);
                            txt_dimension_cult.setText(dimension_cultivo+" hm²");
                            txt_tipoarroz.setText(tipo_arroz);
                            txt_tiposiemb.setText(tipo_siembra);
                            txt_valor_inver.setText("$ "+valor_inversion);



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




    public void obtener_barrasWS(String id_cultivo) {
        String url_optener_info = "http://206.189.165.63/dashboardpastelmovil/"+id_cultivo;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_optener_info, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(getApplication(), " "+response,Toast.LENGTH_SHORT).show();

                        JSONArray json = response.optJSONArray("pastel");
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = json.getJSONObject(0);
                            String almacenamiento = jsonObject.getString("total");

                            jsonObject = json.getJSONObject(1);
                            String cosecha = jsonObject.getString("total");

                            jsonObject = json.getJSONObject(2);
                            String insumos = jsonObject.getString("total");

                            jsonObject = json.getJSONObject(3);
                            String terreno = jsonObject.getString("total");

                            jsonObject = json.getJSONObject(4);
                            String fertilizacion = jsonObject.getString("total");

                            jsonObject = json.getJSONObject(5);
                            String siembra = jsonObject.getString("total");

                            double pre = Double.parseDouble(terreno);
                            double sie = Double.parseDouble(siembra);
                            double fer = Double.parseDouble(fertilizacion);
                            double ferI = Double.parseDouble(insumos);
                            double cos = Double.parseDouble(cosecha);
                            double alm = Double.parseDouble(almacenamiento);

                            generatedata(pre, sie, fer, ferI, cos,alm);

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


}
