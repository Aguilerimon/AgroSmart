package com.example.agrosmart;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.biometrics.BiometricManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.agrosmart.NavigationDrawer.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FingerprintActivity extends AppCompatActivity
{

    String nombre, correo, phone, password, user_id;
    Button btnSwitchVer, btnAccept;
    public static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Obtener JSON de la DB
        getJSON("http://agrosmartamm.000webhostapp.com/agrosmart/getSensor.php", "1");
        getJSON("http://agrosmartamm.000webhostapp.com/agrosmart/getStatus.php", "2");
        getJSON("http://agrosmartamm.000webhostapp.com/agrosmart/getNotifications.php", "3");
        setContentView(R.layout.activity_fingerprint);

        btnSwitchVer = findViewById(R.id.btn_switch_ver);
        btnAccept = findViewById(R.id.btn_accept);

        btnSwitchVer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToCodePhone();
            }
        });

        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
       // BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                goToCodePhone();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                goToCodePhone();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                goToCodePhone();
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(FingerprintActivity.this, executor, new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);

                Bundle datos = getIntent().getExtras();
                nombre = datos.getString("Name");
                correo = datos.getString("Email");
                phone = datos.getString("PhoneNumber");
                password = datos.getString("Password");
                user_id = datos.getString("User_Id");

                Intent intent = new Intent(FingerprintActivity.this, MainActivity.class);
                intent.putExtra("Name",nombre);
                intent.putExtra("Email", correo);
                intent.putExtra("PhoneNumber", phone);
                intent.putExtra("Password", password);
                intent.putExtra("User_Id", user_id);

                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new  BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription(getString(R.string.biometric_success))
                .setNegativeButtonText(getString(R.string.cancel_recover))
                .build();

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                biometricPrompt.authenticate(promptInfo);
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }
    public void goToCodePhone()    {
        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("Name");
        correo = datos.getString("Email");
        phone = datos.getString("PhoneNumber");
        password = datos.getString("Password");
        user_id = datos.getString("User_Id");

        Intent intent = new Intent(FingerprintActivity.this, LoginCodeActivity.class);
        intent.putExtra("Name",nombre);
        intent.putExtra("Email", correo);
        intent.putExtra("PhoneNumber", phone);
        intent.putExtra("Password", password);
        intent.putExtra("User_Id", user_id);

        startActivity(intent);
        finish();
    }

    private void getJSON(final String urlWebService, String tipo) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                if(tipo == "1"){
                    try {
                        cargarTabla(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else if(tipo == "2"){
                    try {
                        cargarStatus(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else if (tipo == "3"){
                    try {
                        cargarNotificaciones(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void cargarTabla(String json) throws JSONException{

        JSONObject jsonGeneral = new JSONObject(json);

        JSONArray jsonAgua = jsonGeneral.getJSONArray("dht");
        JSONArray jsonTierra = jsonGeneral.getJSONArray("fc");
        JSONArray jsonAire = jsonGeneral.getJSONArray("mq");

        for(int i=0; i<jsonAgua.length(); i++){
            JSONObject registroAgua = jsonAgua.getJSONObject(i);

            String keyHumidity = "humidity" + i;
            String valorHumidity = registroAgua.get("humidity").toString();

            String keyTemperature = "temperature" + i;
            String valorTemperature = registroAgua.get("temperature").toString();

            String keyDate = "dateAgua" + i;
            String valorDate = registroAgua.get("date_now").toString();

            bundle.putString(keyHumidity, valorHumidity);
            bundle.putString(keyTemperature, valorTemperature);
            bundle.putString(keyDate, valorDate);

        }

        for(int i=0; i<jsonTierra.length(); i++){
            JSONObject registroTierra = jsonTierra.getJSONObject(i);

            String keyGroundHumidity = "ground_humidity" + i;
            String valorGroundHumidity = registroTierra.get("ground_humidity").toString();

            String keyDate = "dateTierra" + i;
            String valorDate = registroTierra.get("date_now").toString();

            bundle.putString(keyGroundHumidity, valorGroundHumidity);
            bundle.putString(keyDate, valorDate);

        }

        for(int i=0; i<jsonAire.length(); i++){
            JSONObject registoAire = jsonAire.getJSONObject(i);

            String keyAirQuality = "air_quality" + i;
            String valorAirQuality = registoAire.get("air_quality").toString();

            String keyDate = "dateAire" + i;
            String valorDate = registoAire.get("date_now").toString();

            bundle.putString(keyAirQuality, valorAirQuality);
            bundle.putString(keyDate, valorDate);

        }

        //String bundleString = bundle.toString();
        //Toast.makeText(getApplicationContext(), bundleString, Toast.LENGTH_LONG).show();
    }

    private void cargarStatus(String json) throws JSONException{

        JSONObject jsonGeneral = new JSONObject(json);
        JSONObject status = jsonGeneral.getJSONArray("status").getJSONObject(0);

        String dht11 = status.get("status_dht11").toString();
        String mq135 = status.get("status_mq135").toString();
        String fc28 = status.get("status_fc28").toString();
        String date = status.get("date_now").toString();

        bundle.putString("status_dht11", dht11);
        bundle.putString("status_mq135", mq135);
        bundle.putString("status_fc28", fc28);
        bundle.putString("date_now", date);

    }

    private void cargarNotificaciones(String json) throws JSONException{

        JSONObject jsonGeneral = new JSONObject(json);
        JSONArray arrayNotif = jsonGeneral.getJSONArray("notif");

        for(int i=0; i<arrayNotif.length(); i++){

            JSONObject notificacion = arrayNotif.getJSONObject(i);

            String keyNotif = "notif" + i;
            String notif = notificacion.get("notification").toString();

            String keyDate = "dateNotif" + i;
            String fecha = notificacion.get("date_now").toString();

            bundle.putString(keyNotif,  notif);
            bundle.putString(keyDate, fecha);

        }
    }
}
