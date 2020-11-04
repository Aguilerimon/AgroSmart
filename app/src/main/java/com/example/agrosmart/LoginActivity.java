package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{

    private ProgressDialog progressDialog, progressDialogForgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRecoverPass = findViewById(R.id.btn_ir_recuperar_contrasena);
        final EditText email = findViewById(R.id.edtEmail);
        final EditText password = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
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

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("Name",Name);
                                intent.putExtra("Email", Email);
                                intent.putExtra("PhoneNumber", Phone);
                                intent.putExtra("Password", Pass);

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
                showRecoveryPasswordDialog();
            }
        });
    }

    private void showRecoveryPasswordDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle(getString(R.string.recover_password));
        alertDialog.setMessage(getString(R.string.message_dialog_recovery));

        final EditText input = new EditText(LoginActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getString(R.string.recover),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(LoginActivity.this, R.string.email_sent, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

        alertDialog.setNegativeButton(getString(R.string.cancel_recover),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
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
}