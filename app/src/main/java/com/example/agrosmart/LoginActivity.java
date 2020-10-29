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

        Button btnCreateAccount = findViewById(R.id.btn_create_account);
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

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean successResponse = jsonResponse.getBoolean("success");

                            if(successResponse == true)
                            {
                                Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_LONG).show();
                                String Name = jsonResponse.getString("Name");
                                String Email = jsonResponse.getString("Email");
                                String Phone = jsonResponse.getString("PhoneNumber");
                                String Pass = jsonResponse.getString("Password");

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("Name",Name);
                                intent.putExtra("Email", Email);
                                intent.putExtra("PhoneNumber", Phone);
                                intent.putExtra("Password", Pass);

                                LoginActivity.this.startActivity(intent);

                            }
                            else
                            {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                alert.setMessage(R.string.login_error).setNegativeButton(R.string.retry, null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, R.string.login_error + ": " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                };

                LoginRequest loginResponse = new LoginRequest(Email,Password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginResponse);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener()
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