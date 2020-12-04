package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{

    private ProgressDialog progressDialog, progressDialogForgotPass;
    Button btnCreateAccount, btnLogin, btnRecoverPass;
    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnLogin = findViewById(R.id.btnLogin);
        btnRecoverPass = findViewById(R.id.btn_ir_recuperar_contrasena);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateLoginFields();
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

        btnRecoverPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, RecoverPassCodeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void initProgressDialog()
    {
        this.progressDialog = new ProgressDialog(this);
    }

    private void showProgressDialog()
    {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.progress_dialog));
        progressDialog.show();
    }

    public void validateLoginFields()
    {
        if(email.getText().toString().isEmpty())
            password.setError(getString(R.string.empty_email));
        if(password.getText().toString().isEmpty())
            password.setError(getString(R.string.empty_password));
        else
        {
            final String Email = email.getText().toString();
            final String Password = password.getText().toString();
            initProgressDialog();
            showProgressDialog();

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
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                            String Name = jsonResponse.getString("Name");
                            String Email = jsonResponse.getString("Email");
                            String Phone = jsonResponse.getString("PhoneNumber");
                            String Pass = jsonResponse.getString("Password");
                            String User_Id = jsonResponse.getString("User_Id");

                            Intent intent = new Intent(LoginActivity.this, FingerprintActivity.class);
                            intent.putExtra("Name",Name);
                            intent.putExtra("Email", Email);
                            intent.putExtra("PhoneNumber", Phone);
                            intent.putExtra("Password", Pass);
                            intent.putExtra("User_Id", User_Id);

                            LoginActivity.this.startActivity(intent);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setMessage(R.string.login_error).setNegativeButton(R.string.retry, null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.login_error + ": " + e, Toast.LENGTH_LONG).show();
                    }
                }
            };

            LoginRequest loginResponse = new LoginRequest(Email,Password,responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginResponse);
        }

    }


}