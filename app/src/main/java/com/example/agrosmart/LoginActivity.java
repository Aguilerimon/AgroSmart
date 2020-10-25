package com.example.agrosmart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        Button btn_irCuenta = findViewById(R.id.btn_ir_crearCuenta);
        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText email = findViewById(R.id.edtEmail);
        final EditText password = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String Email = email.getText().toString();
                final String Password = password.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean exito = jsonRespuesta.getBoolean("success");//Del archivo de conexion

                            if(exito == true)
                            {
                                Toast.makeText(LoginActivity.this, "Credenciales validadas", Toast.LENGTH_LONG).show();
                                String Name = jsonRespuesta.getString("Name");
                                String Email = jsonRespuesta.getString("Email");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("Name",Name);
                                intent.putExtra("Email", Email);

                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();

                            }
                            else
                            {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(LoginActivity.this);
                                alerta.setMessage("Error en login").setNegativeButton("Reintentar", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                };

                LoginRequest loginRespuesta = new LoginRequest(Email,Password,respuesta);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRespuesta);
            }
        });

        btn_irCuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}