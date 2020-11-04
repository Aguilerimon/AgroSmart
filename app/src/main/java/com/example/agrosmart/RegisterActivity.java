package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        Button btnRegister = findViewById(R.id.btn_register);
        final EditText name = findViewById(R.id.edt_name);
        final EditText email = findViewById(R.id.edt_email);
        final EditText phone = findViewById(R.id.edt_phone);
        final EditText  password = findViewById(R.id.edt_password);


        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Name = name.getText().toString();
                String PhoneNumber = phone.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
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
                                showConfirmCode();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
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

    private void showConfirmCode()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
        alertDialog.setTitle(getString(R.string.confirm_code));
        alertDialog.setMessage(getString(R.string.confirm_code_desc));

        final EditText input = new EditText(RegisterActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getString(R.string.accept),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(RegisterActivity.this, R.string.passed, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        RegisterActivity.this.finish();
                        Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
    }
}