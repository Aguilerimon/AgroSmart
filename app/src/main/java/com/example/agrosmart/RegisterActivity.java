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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        Button btnRegister = findViewById(R.id.btn_register);
        final EditText name = findViewById(R.id.edt_name);
        final EditText email = findViewById(R.id.edt_email);
        final EditText phone = findViewById(R.id.edt_phone);
        final EditText  password = findViewById(R.id.edt_password);
        progressBar = findViewById(R.id.loading);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Name = name.getText().toString();
                String PhoneNumber = phone.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

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
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                                RegisterActivity.this.finish();
                                Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, R.string.register_error + ": " + e, Toast.LENGTH_LONG).show();
                        }
                    }

                };

                RegisterRequest registerResponse = new RegisterRequest(Name,PhoneNumber,Email,Password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerResponse);
            }
        });

    }
}