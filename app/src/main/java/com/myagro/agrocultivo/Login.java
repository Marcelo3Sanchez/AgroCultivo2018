package com.myagro.agrocultivo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myagro.agrocultivo.Entidades.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.Manifest.permission.READ_CONTACTS;


public class Login extends Activity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private home mAuthTask = null;
    String id;

    AutoCompleteTextView txtUser;
    EditText txtPass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtUser=(AutoCompleteTextView) findViewById(R.id.txtUser);
        txtPass=(EditText)findViewById(R.id.txtPass);

         txtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.btnIngresar);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }



    private void sesion(){
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String user =txtUser.getText().toString();
        String pass=txtPass.getText().toString();
        String idCult = null;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",id);
        editor.putString("user",user);
        editor.putString("pass",pass);
        editor.putString("idCultivo", idCult);
        editor.commit();
    }




    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        txtUser.setError(null);
        txtPass.setError(null);

        // Store values at the time of the login attempt.
        String email = txtUser.getText().toString();
        String password = txtPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtPass.setError(getString(R.string.error_invalid_password));
            focusView = txtPass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            txtUser.setError(getString(R.string.error_field_required));
            focusView = txtUser;
            cancel = true;

        } else if (!isEmailValid(email)) {
            txtUser.setError(getString(R.string.error_invalid_email));
            focusView = txtUser;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {


            Thread tr = new Thread() {
                @Override
                public void run() {
                    final String resultado = enviarDatosGET(txtUser.getText().toString(), txtPass.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r = obtenerDatosJSON(resultado);
                            if (r > 0) {

                                sesion();
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        finish();
                                        Intent home = new Intent(getApplicationContext(), home.class);
                                        startActivity(home);
                                        home.putExtra("cod", txtUser.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Secion Iniciada", Toast.LENGTH_LONG).show();

                                    }

                                    ;
                                }, 2000);

                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o Password Incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };

            tr.start();

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }





    public String enviarDatosGET(String usu, String pas){
        URL url = null;
        String linea="";
        int respuesta =0;
        StringBuilder resul = null;
        try{
            url = new URL(getString(R.string.host)+"validar.php?usu="+usu+"&pas="+pas);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();
            resul= new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine())!=null){
                    resul.append(linea);
                }
            }

        }catch (Exception e){}
        return resul.toString();
    }

    public int obtenerDatosJSON(String response){
        User usu = new User();
        JSONObject jsonObject = null;

        int res=0;
        try{
            JSONArray json = new JSONArray(response);
            if (json.length()>0){
                res=1;
                jsonObject = json.getJSONObject(0);
                usu.setId(jsonObject.getString("id"));
                id=usu.getId().toString();

            }
        }catch (Exception e){}
        return res;
    }


}
